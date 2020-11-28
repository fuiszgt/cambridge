#version 140

in vec3 wc_frag_normal;        	// fragment normal in world coordinates (wc_)
in vec2 frag_texcoord;			// texture UV coordinates
in vec3 wc_frag_pos;			// fragment position in world coordinates

out vec3 color;			        // pixel colour

uniform sampler2D tex;  		  // 2D texture sampler
uniform samplerCube skybox;		  // Cubemap texture used for reflections
uniform vec3 wc_camera_position;  // Position of the camera in world coordinates

// Tone mapping and display encoding combined
vec3 tonemap(vec3 linearRGB)
{
    float L_white = 1.0; // Controls the brightness of the image

    float inverseGamma = 1./2.2;
    return pow(linearRGB/L_white, vec3(inverseGamma)); // Display encoding - a gamma
}

const vec3 P_1 = vec3(1,1,1);
const vec3 I_1 = vec3(0.3, 0.3, 0.9); //bright blue light source
const vec3 I_a = vec3(0.6, 0.6, 0.6); //White ambient light
const vec3 k_a = vec3(0.5, 0.5, 0.5); //grey cube
const vec3 k_d = vec3(0.4, 0.4, 0.4); //diffuse reflection: white
const vec3 k_s = vec3(0.8, 0.8, 0.8); //specular reflection: white


void main()
{
	vec3 linear_color = vec3(0, 0, 0);
	// TODO: Calculate colour using Phong illumination model
    //vec3 R = normalize(P_1 - wc_frag_pos);
    vec3 L = normalize(P_1); //infinitely far away;
    vec3 R = (-1) * reflect(L, wc_frag_normal);
    vec3 V = normalize(wc_camera_position - wc_frag_pos);
    vec3 ambient = I_a * k_a;
    vec3 diffuse = I_1 * k_d * max(0, dot(L, wc_frag_normal));
    vec3 specular = I_1 * k_s * pow(max(0, dot(R, V)), 10000);

    linear_color = ambient + diffuse + specular;
	// TODO: Sample the texture and replace diffuse surface colour (C_diff) with texel value


	color = tonemap(linear_color);
}

