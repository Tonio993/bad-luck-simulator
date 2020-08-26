varying vec4 v_color;
varying vec2 v_texCoords;
uniform sampler2D u_texture;

uniform float u_fade;
uniform vec4 u_fadeColor;

void main()
{
	vec4 texture = texture2D(u_texture, v_texCoords);
	vec4 finalTexture = vec4(vec3(texture.rgb * (1 - u_fade) + u_fadeColor.rgb * u_fade), texture.a);
	gl_FragColor = v_color * finalTexture;
}