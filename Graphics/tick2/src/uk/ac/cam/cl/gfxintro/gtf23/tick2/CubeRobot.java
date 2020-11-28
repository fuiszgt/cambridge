package uk.ac.cam.cl.gfxintro.gtf23.tick2;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glGetError;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.lang.Math;
import java.nio.FloatBuffer;;

public class CubeRobot {
	
    // Filenames for vertex and fragment shader source code
    private final static String VSHADER_FN = "resources/cube_vertex_shader.glsl";
    private final static String FSHADER_FN = "resources/cube_fragment_shader.glsl";
    
    // Reference to skybox of the scene
    public SkyBox skybox;
    
    // Components of this CubeRobot
    
    // Component 1 : Body
	private Mesh body_mesh;				// Mesh of the body
	private ShaderProgram body_shader;	// Shader to colour the body mesh
	private Texture body_texture;		// Texture image to be used by the body shader
	private Matrix4f body_transform;	// Transformation matrix of the body object
	
	// Component 2: Right Arm
	private Mesh right_arm_mesh;				// Mesh of the right_arm
	private ShaderProgram right_arm_shader;	// Shader to colour the right_arm mesh
	private Texture right_arm_texture;		// Texture image to be used by the right_arm shader
	private Matrix4f right_arm_transform;	// Transformation matrix of the right_arm object
	
	// Complete rest of the robot

	
/**
 *  Constructor
 *  Initialize all the CubeRobot components
 */
	public CubeRobot() {
		// Create body node
		
		// Initialise Geometry
		body_mesh = new CubeMesh(); 
		
		// Initialise Shader
		body_shader = new ShaderProgram(new Shader(GL_VERTEX_SHADER, VSHADER_FN), new Shader(GL_FRAGMENT_SHADER, FSHADER_FN), "colour");
		// Tell vertex shader where it can find vertex positions. 3 is the dimensionality of vertex position
		// The prefix "oc_" means object coordinates
		body_shader.bindDataToShader("oc_position", body_mesh.vertex_handle, 3);
		// Tell vertex shader where it can find vertex normals. 3 is the dimensionality of vertex normals
		body_shader.bindDataToShader("oc_normal", body_mesh.normal_handle, 3);
		// Tell vertex shader where it can find texture coordinates. 2 is the dimensionality of texture coordinates
		body_shader.bindDataToShader("texcoord", body_mesh.tex_handle, 2);
		
		// Initialise Texturing
		body_texture = new Texture(); 
		body_texture.load("resources/cubemap.png");
		
		// Build Transformation Matrix
		body_transform = new Matrix4f();

		// Scale the body transformation matrix
		body_transform.scale(1,2,1);
		//Create right arm

		// TODO: extract this into a method

		// Initialise Geometry
		right_arm_mesh = new CubeMesh();

		// Initialise Shader
		right_arm_shader = new ShaderProgram(new Shader(GL_VERTEX_SHADER, VSHADER_FN), new Shader(GL_FRAGMENT_SHADER, FSHADER_FN), "colour");
		// Tell vertex shader where it can find vertex positions. 3 is the dimensionality of vertex position
		// The prefix "oc_" means object coordinates
		right_arm_shader.bindDataToShader("oc_position", right_arm_mesh.vertex_handle, 3);
		// Tell vertex shader where it can find vertex normals. 3 is the dimensionality of vertex normals
		right_arm_shader.bindDataToShader("oc_normal", right_arm_mesh.normal_handle, 3);
		// Tell vertex shader where it can find texture coordinates. 2 is the dimensionality of texture coordinates
		right_arm_shader.bindDataToShader("texcoord", right_arm_mesh.tex_handle, 2);

		// Initialise Texturing
		right_arm_texture = new Texture();
		right_arm_texture.load("resources/cubemap.png");

		// Build Transformation Matrix
		right_arm_transform = new Matrix4f();

		// Scale the right_arm transformation matrix
		right_arm_transform.rotateAffineXYZ(-0.523599f,0, 0).scale(0.25f,2,0.25f).translate(0,-1,0);
		Matrix4f arm_position = new Matrix4f().translate(0,2,1);
		right_arm_transform = arm_position.mul(right_arm_transform);

		
		// TODO: Initialise Texturing

		
		// TODO: Build Right Arm's Transformation Matrix (rotate the right arm around its end)

		
		// TODO: Complete robot

	}
	

	/**
	 * Updates the scene and then renders the CubeRobot
	 * @param camera - Camera to be used for rendering
	 * @param deltaTime		- Time taken to render this frame in seconds (= 0 when the application is paused)
	 * @param elapsedTime	- Time elapsed since the beginning of this program in millisecs
	 */
	public void render(Camera camera, float deltaTime, long elapsedTime) {
		
		// TODO: Animate Body. Translate the body as a function of time

		
		// TODO: Animate Arm. Rotate the left arm around its end as a function of time

		
		renderMesh(camera, body_mesh, body_transform, body_shader, body_texture);

		// TODO: Chain transformation matrices of the arm and body (Scene Graph)
		// TODO: Render Arm.
		renderMesh(camera, right_arm_mesh, right_arm_transform, right_arm_shader, right_arm_texture);
		
		//TODO: Render rest of the robot

		
		

		
	}
	
	/**
	 * Draw mesh from a camera perspective
	 * @param camera		- Camera to be used for rendering
	 * @param mesh			- mesh to render
	 * @param modelMatrix	- model transformation matrix of this mesh
	 * @param shader		- shader to colour this mesh
	 * @param texture		- texture image to be used by the shader
	 */
	public void renderMesh(Camera camera, Mesh mesh , Matrix4f modelMatrix, ShaderProgram shader, Texture texture) {
		// If shaders modified on disk, reload them
		shader.reloadIfNeeded(); 
		shader.useProgram();

		// Step 2: Pass relevant data to the vertex shader
		
		// compute and upload MVP
		Matrix4f mvp_matrix = new Matrix4f(camera.getProjectionMatrix()).mul(camera.getViewMatrix()).mul(modelMatrix);
		shader.uploadMatrix4f(mvp_matrix, "mvp_matrix");
		
		// Upload Model Matrix and Camera Location to the shader for Phong Illumination
		shader.uploadMatrix4f(modelMatrix, "m_matrix");
		shader.uploadVector3f(camera.getCameraPosition(), "wc_camera_position");
		
		// Transformation by a nonorthogonal matrix does not preserve angles
		// Thus we need a separate transformation matrix for normals

		//It is the transpose of the inverse of the model matrix (But only the upper-3 coordinates.)
		Matrix3f normal_matrix = new Matrix3f(modelMatrix).invert().transpose();
		
		shader.uploadMatrix3f(normal_matrix, "normal_matrix");
		
		// Step 3: Draw our VertexArray as triangles
		// Bind Textures
		texture.bindTexture();
		shader.bindTextureToShader("tex", 0);
		skybox.bindCubemap();
		shader.bindTextureToShader("skybox", 1);
		// draw
		glBindVertexArray(mesh.vertexArrayObj); // Bind the existing VertexArray object
		glDrawElements(GL_TRIANGLES, mesh.no_of_triangles, GL_UNSIGNED_INT, 0); // Draw it as triangles
		glBindVertexArray(0);             // Remove the binding
		
        // Unbind texture
		texture.unBindTexture();
		skybox.unBindCubemap();
	}
}
