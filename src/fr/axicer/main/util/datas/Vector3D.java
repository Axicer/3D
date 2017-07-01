package fr.axicer.main.util.datas;

public class Vector3D extends Vector2D{
	
	public double z;
	
	public Vector3D(double x, double y, double z) {
		super(x, y);
		this.z = z;
	}
	public Vector3D() {
		this(0,0,0);
	}
	
	public Vector3D setX(double x){
		this.x = x;
		return this;
	}
	public Vector3D setY(double y){
		this.y = y;
		return this;
	}
	public Vector3D addX(double x){
		this.x += x;
		return this;
	}
	public Vector3D subX(double x){
		this.x -= x;
		return this;
	}
	public Vector3D divX(double x){
		this.x /= x;
		return this;
	}
	public Vector3D mulX(double x){
		this.x *= x;
		return this;
	}
	public Vector3D modX(double x){
		this.x %= x;
		return this;
	}
	public Vector3D addY(double y){
		this.y += y;
		return this;
	}
	public Vector3D subY(double y){
		this.y -= y;
		return this;
	}
	public Vector3D divY(double y){
		this.y /= y;
		return this;
	}
	public Vector3D mulY(double y){
		this.y *= y;
		return this;
	}
	public Vector3D modY(double y){
		this.y %= y;
		return this;
	}
	public Vector3D setZ(double z){
		this.z = z;
		return this;
	}
	public double getZ(){
		return this.z;
	}
	public Vector3D addZ(double z){
		this.z += z;
		return this;
	}
	public Vector3D subZ(double z){
		this.z -= z;
		return this;
	}
	public Vector3D divZ(double z){
		this.z /= z;
		return this;
	}
	public Vector3D mulZ(double z){
		this.z *= z;
		return this;
	}
	public Vector3D modZ(double z){
		this.z %= z;
		return this;
	}
	
	public Vector3D add(double v) {
		this.x += v;
		this.y += v;
		this.z += v;
		return this;
	}
	public Vector3D add(Vector3D v) {
		this.x += v.x;
		this.y += v.y;
		this.z += v.z;
		return this;
	}
	public Vector3D sub(double v) {
		this.x -= v;
		this.y -= v;
		this.z -= v;
		return this;
	}
	public Vector3D sub(Vector3D v) {
		this.x -= v.x;
		this.y -= v.y;
		this.z -= v.z;
		return this;
	}
	public Vector3D mul(double v) {
		this.x *= v;
		this.y *= v;
		this.z *= v;
		return this;
	}
	public Vector3D mul(Vector3D v) {
		this.x *= v.x;
		this.y *= v.y;
		this.z *= v.z;
		return this;
	}
	public Vector3D div(double v) {
		this.x /= v;
		this.y /= v;
		this.z /= v;
		return this;
	}
	public Vector3D div(Vector3D v) {
		this.x /= v.x;
		this.y /= v.y;
		this.z /= v.z;
		return this;
	}
	public Vector3D mod(double v) {
		this.x %= v;
		this.y %= v;
		this.z %= v;
		return this;
	}
	public Vector3D mod(Vector3D v) {
		this.x %= v.x;
		this.y %= v.y;
		this.z %= v.z;
		return this;
	}
	public Vector3D set(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	public Vector3D set(Vector3D v){
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
		return this;
	}
	
	@Override
	public double length() {
		return Math.sqrt(x*x+y*y+z*z);
	}
	public Vector3D normalize() {
		double l = length();
		x /= l;
		y /= l;
		z /= l;
		return this;
	}
	public Vector3D copy() {
		return new Vector3D(x, y, z);
	}
	
	public Vector3D check(){
		double max = Math.max(Math.max(x, y), z);
		double min = Math.min(Math.min(x, y), z);
		
		double absMax = Math.abs(max-1);
		double absMin = Math.abs(min);
		
		double v = 0;
		if(absMax > absMin) v = min;
		else v = max;
		int rv = 1;
		
		if(v < 0.5f)rv = -1;
		
		return new Vector3D(v == x ? rv : 0, v == y ? rv : 0, v == z ? rv : 0);
	}
}
