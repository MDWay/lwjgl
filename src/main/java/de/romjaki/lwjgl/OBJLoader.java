package de.romjaki.lwjgl;

import com.mokiat.data.front.parser.*;
import de.romjaki.lwjgl.renderer.DefaultShader;
import de.romjaki.lwjgl.renderer.Texture;
import de.romjaki.lwjgl.renderer.geometric.TexturedModel;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;


/**
 * Created by RGR on 24.07.2017.
 */
public class OBJLoader {
    private static final IOBJParser objParser = new OBJParser();
    private static final IMTLParser mtlParser = new MTLParser();

    public static TexturedModel loadOBJ(String modelName, String textureName) {
        File f = new File("src/main/resources/models/" + modelName);
        Texture t = new Texture(textureName, 0);
        try (Scanner s = new Scanner(f)) {
            List<Vector3f> normals = new ArrayList<>();
            List<Vector2f> texCoords = new ArrayList<>();
            List<Integer> indices = new ArrayList<>();
            List<Vector3f> vertices = new ArrayList<>();
            float[] texArray = null;
            float[] normalsArray = null;
            String line = null;
            while (s.hasNextLine()) {
                line = s.nextLine();
                String[] cur = line.split("\\s+");
                if (cur[0].equalsIgnoreCase("v")) {//parse Vertex
                    vertices.add(new Vector3f(Float.parseFloat(cur[1]), Float.parseFloat(cur[2]), Float.parseFloat(cur[3])));
                } else if (cur[0].equalsIgnoreCase("vt")) {//parse TextureCoords
                    texCoords.add(new Vector2f(Float.parseFloat(cur[1]), Float.parseFloat(cur[2])));
                } else if (cur[0].equalsIgnoreCase("vn")) {//parse normal
                    normals.add(new Vector3f(Float.parseFloat(cur[1]), Float.parseFloat(cur[2]), Float.parseFloat(cur[3])));
                } else if (cur[0].equalsIgnoreCase("f")) {//parse Face
                    texArray = new float[vertices.size() * 2];
                    normalsArray = new float[vertices.size() * 3];
                    break;
                }
            }
            while (line != null) {
                if (!line.startsWith("f ")) {
                    if (!s.hasNextLine()) {
                        break;
                    }
                    line = s.nextLine();
                    continue;
                }
                String[] cur = line.split("\\s+");
                if (line.contains("/")) {
                    String[] vert1 = cur[1].split("/");
                    String[] vert2 = cur[2].split("/");
                    String[] vert3 = cur[3].split("/");

                    proccessVertex(vert1, indices, texCoords, normals, texArray, normalsArray);
                    proccessVertex(vert2, indices, texCoords, normals, texArray, normalsArray);
                    proccessVertex(vert3, indices, texCoords, normals, texArray, normalsArray);
                } else {
                    String[] vert = new String[3];
                    vert[0] = cur[1];
                    vert[1] = cur[2];
                    vert[2] = cur[3];
                    proccessVertex(vert, indices, texCoords, normals, texArray, normalsArray);
                }

                if (!s.hasNextLine()) {
                    break;
                }
                line = s.nextLine();
            }
            float[] verticesArray = new float[vertices.size() * 3];
            int[] indicesArray = new int[indices.size()];

            int vertexPointer = 0;
            for (Vector3f vertex : vertices) {
                verticesArray[vertexPointer++] = vertex.x;
                verticesArray[vertexPointer++] = vertex.y;
                verticesArray[vertexPointer++] = vertex.z;
            }

            for (int i = 0; i < indicesArray.length; i++) {
                indicesArray[i] = indices.get(i);
            }

            return new TexturedModel(t, verticesArray, indicesArray, texArray, normalsArray, DefaultShader.INSTANCE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void proccessVertex(String[] vertData, List<Integer> indices, List<Vector2f> textures, List<Vector3f> normals, float[] textureArray, float[] normalsArray) {
        int currentVertexPointer = Integer.parseInt(vertData[0]) - 1;
        indices.add(currentVertexPointer);
        Vector2f textureCoords = textures.get(Integer.parseInt(vertData[1]) - 1);
        textureArray[currentVertexPointer * 2] = textureCoords.x;
        textureArray[currentVertexPointer * 2 + 1] = 1 - textureCoords.y;
        Vector3f currentNormal = normals.get(Integer.parseInt(vertData[2]) - 1);
        normalsArray[currentVertexPointer * 3] = currentNormal.x;
        normalsArray[currentVertexPointer * 3 + 1] = currentNormal.y;
        normalsArray[currentVertexPointer * 3 + 2] = currentNormal.z;
    }


    public static TexturedModel[] loadOBJE(String file, String textureName) {
        List<TexturedModel> ret = new ArrayList<>();
        try (FileInputStream stream = new FileInputStream(new File("src/main/resources/models/" + file))) {
            OBJModel model = objParser.parse(stream);
            Texture texture = new Texture(textureName, 0);

            Stream<Vector3f> vertexStream = model.getVertices().stream().map(objVertex -> new Vector3f(objVertex.x, objVertex.y, objVertex.z));
            float[] vertexArr = new float[model.getVertices().size() * 3];
            int[] index = new int[]{0};
            vertexStream.forEach(vector3f -> {
                vertexArr[index[0]++] = vector3f.x;
                vertexArr[index[0]++] = vector3f.y;
                vertexArr[index[0]++] = vector3f.z;
            });
            GLArrayBuffer vertices = new GLArrayBuffer(vertexArr, 3);

            Stream<Vector3f> normalStream = model.getNormals().stream().map(objNormal -> new Vector3f(objNormal.x, objNormal.y, objNormal.z));
            float[] normalArray = new float[model.getNormals().size() * 3];
            index[0] = 0;
            normalStream.forEach(vector3f -> {
                normalArray[index[0]++] = vector3f.x;
                normalArray[index[0]++] = vector3f.y;
                normalArray[index[0]++] = vector3f.z;
            });
            GLArrayBuffer normals = new GLArrayBuffer(normalArray, 3);

            Stream<Vector3f> texCoordsStream = model.getTexCoords().stream().map(objTexCoord -> new Vector3f(objTexCoord.u, objTexCoord.v, objTexCoord.w));
            float[] texCoordsArray = new float[model.getTexCoords().size() * 3];
            index[0] = 0;
            texCoordsStream.forEach(vector2f -> {
                texCoordsArray[index[0]++] = vector2f.x;
                texCoordsArray[index[0]++] = vector2f.y;
                texCoordsArray[index[0]++] = vector2f.z;
            });
            GLArrayBuffer texCoords = new GLArrayBuffer(texCoordsArray, 3);

            for (OBJObject obj : model.getObjects()) {

                List<Integer> indices = new ArrayList<>();
                for (OBJMesh mesh : obj.getMeshes()) {
                    String materialName = mesh.getMaterialName();
                    for (OBJFace face : mesh.getFaces()) {
                        for (OBJDataReference reference : face.getReferences()) {
                            OBJVertex vertex = model.getVertex(reference);
                            indices.add(reference.vertexIndex);
                        }
                    }
                }
                int[] indicesArray = new int[indices.size()];
                int indexI = 0;
                for (int i : indices) {
                    indicesArray[indexI++] = i;
                }
                ret.add(new TexturedModel(texture, vertices, new GLElementBuffer(indicesArray), texCoords, normals, DefaultShader.INSTANCE));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret.toArray(new TexturedModel[ret.size()]);
    }
}
