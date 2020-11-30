package uk.ac.cam.cl.gfxintro.gtf23.tick2;

import org.joml.Matrix3f;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.util.ArrayList;
import java.util.List;;

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
	private ShaderProgram arm_shader;	// Shader to colour the right_arm mesh
	private Texture arm_texture;		// Texture image to be used by the right_arm shader
	private Matrix4f right_arm_transform;	// Transformation matrix of the right_arm object
	
	// Complete rest of the robot

	CubeNode body, rArm, lArm, rLeg, lLeg, head;

	class CubeNode {

		public static final String RESOURCES_CUBEMAP_PNG = "resources/cubemap.png";

		public CubeNode(){
			this(RESOURCES_CUBEMAP_PNG);
		}

		public CubeNode(String textureFile) {
			// Initialise Shader
			mesh = new CubeMesh();
			// Initialise Texturing
			texture = new Texture();
			texture.load(textureFile);

			// Initialise Shader
			shader = new ShaderProgram(new Shader(GL_VERTEX_SHADER, VSHADER_FN), new Shader(GL_FRAGMENT_SHADER, FSHADER_FN), "colour");
			// Tell vertex shader where it can find vertex positions. 3 is the dimensionality of vertex position
			// The prefix "oc_" means object coordinates
			shader.bindDataToShader("oc_position", mesh.vertex_handle, 3);
			// Tell vertex shader where it can find vertex normals. 3 is the dimensionality of vertex normals
			shader.bindDataToShader("oc_normal", mesh.normal_handle, 3);
			// Tell vertex shader where it can find texture coordinates. 2 is the dimensionality of texture coordinates
			shader.bindDataToShader("texcoord", mesh.tex_handle, 2);
			this.model = new Matrix4f();
			this.animation = new Matrix4f();
			this.view = new Matrix4f();
		}

		private Mesh mesh;				// Mesh of the body
		private ShaderProgram shader;	// Shader to colour the body mesh
		private Texture texture;		// Texture image to be used by the body shader
		private Matrix4f model;
		private Matrix4f view; // translation to position
		private Matrix4f animation;

		private List<CubeNode> parts = new ArrayList<CubeNode>();

		public void render(Camera camera){
			Matrix4f transform = new Matrix4f();
			transform.mul(animation);
			transform.mul(view);
			transform.mul(model);
			renderMesh(camera, mesh, transform, shader, texture);
			for (CubeNode node: parts) {
				node.render(camera);
			}
		}

		public void setModel(Matrix4f model) {
			this.model = model;
		}

		public void transform(Matrix4f transformation){
			transformation.mul(model, model);
			for(CubeNode node:parts){
				node.propagate(transformation);
			}
		}

		public void propagate(Matrix4f transformation){
			transformation.mul(animation, animation);
			for(CubeNode node:parts){
				node.propagate(transformation);
			}
		}



		public void append(CubeNode part){
			parts.add(part);
		}

		public void setView(Matrix4f view) {
			this.view = view;
		}
	}
	
/**
 *  Constructor
 *  Initialize all the CubeRobot components
 */


	public CubeRobot() {



		body = new CubeNode();
		body.setModel(new Matrix4f().scale(1,2,1));

		rArm = new CubeNode();
		lArm = new CubeNode();
		lLeg = new CubeNode();
		rLeg = new CubeNode();
		head = new CubeNode("resources/cubemap_head.png");

		rArm.setModel(new Matrix4f().rotateAffineXYZ(0,0, -0.523599f).scale(0.25f,2,0.25f).translate(0,-1,0));
		lArm.setModel(new Matrix4f().rotateAffineXYZ(0,0, 0.523599f).scale(0.25f,2,0.25f).translate(0,-1,0));

		rArm.setView(new Matrix4f().translate(-1,2,0));
		lArm.setView(new Matrix4f().translate(1,2,0));

		Matrix4f leg_shape = new Matrix4f().scale(0.25f,1.5f, 0.25f);
		lLeg.setModel(leg_shape);
		rLeg.setModel(leg_shape);
		rLeg.setView(new Matrix4f().translate(0.5f,-2, 0));
		lLeg.setView(new Matrix4f().translate(-0.5f,-2, 0));
		head.setModel(new Matrix4f().scale(0.75f, 0.5f,0.75f));
		head.setView(new Matrix4f().translate(0,2.5f,0));

		body.append(rArm);
		body.append(lArm);
		body.append(lLeg);
		body.append(rLeg);
		body.append(head);



		// TODO: Initialise Texturing


	}


	/**
	 * Updates the scene and then renders the CubeRobot
	 * @param camera - Camera to be used for rendering
	 * @param deltaTime		- Time taken to render this frame in seconds (= 0 when the application is paused)
	 * @param elapsedTime	- Time elapsed since the beginning of this program in millisecs
	 */
	public void render(Camera camera, float deltaTime, long elapsedTime) {
		body.transform(new Matrix4f().rotateAffineXYZ(0, 2.0f*(deltaTime),0));
		rArm.transform(new Matrix4f().rotateAffineXYZ(2.0f*deltaTime,0,0));

		body.render(camera);
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
