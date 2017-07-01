package fr.axicer.main.util.datas;

public class Vector2D {
	
	public double x,y;
	
	public Vector2D() {
		this(0,0);
	}
	public Vector2D(double x, double y){
		this.x = x;
		this.y = y;
	}
	public Vector2D(Vector2D vec){
		this.x = vec.x;
		this.y = vec.y;
	}
	public Vector2D setX(double x){
		this.x = x;
		return this;
	}
	public Vector2D setY(double y){
		this.y = y;
		return this;
	}
	public double getX(){
		return this.x;
	}
	public double getY(){
		return this.y;
	}
	public Vector2D addX(double x){
		this.x += x;
		return this;
	}
	public Vector2D subX(double x){
		this.x -= x;
		return this;
	}
	public Vector2D divX(double x){
		this.x /= x;
		return this;
	}
	public Vector2D mulX(double x){
		this.x *= x;
		return this;
	}
	public Vector2D modX(double x){
		this.x %= x;
		return this;
	}
	public Vector2D addY(double y){
		this.y += y;
		return this;
	}
	public Vector2D subY(double y){
		this.y -= y;
		return this;
	}
	public Vector2D divY(double y){
		this.y /= y;
		return this;
	}
	public Vector2D mulY(double y){
		this.y *= y;
		return this;
	}
	public Vector2D modY(double y){
		this.y %= y;
		return this;
	}
	
	public Vector2D mul(double v){
		this.x *= v;
		this.y *= v;
		return this;
	}
	public Vector2D mul(Vector2D v){
		this.x *= v.x;
		this.y *= v.y;
		return this;
	}
	public Vector2D div(double v){
		this.x /= v;
		this.y /= v;
		return this;
	}
	public Vector2D div(Vector2D v){
		this.x /= v.x;
		this.y /= v.y;
		return this;
	}
	public Vector2D add(double v){
		this.x += v;
		this.y += v;
		return this;
	}
	public Vector2D add(Vector2D v){
		this.x += v.x;
		this.y += v.y;
		return this;
	}
	public Vector2D sub(double v){
		this.x -= v;
		this.y -= v;
		return this;
	}
	public Vector2D sub(Vector2D v){
		this.x -= v.x;
		this.y -= v.y;
		return this;
	}
	public Vector2D mod(double v){
		this.x %= v;
		this.y %= v;
		return this;
	}
	public Vector2D mod(Vector2D v){
		this.x %= v.x;
		this.y %= v.y;
		return this;
	}
	public Vector2D set(double x, double y){
		this.x = x;
		this.y = y;
		return this;
	}
	public Vector2D set(Vector2D v){
		this.x = v.x;
		this.y = v.y;
		return this;
	}
	
	public Vector2D normalize(){
		double l = length();
		this.x /= l;
		this.y /= l;
		return this;
	}
	public double length(){
		return Math.sqrt(x*x+y*y);
	}
	public Vector2D copy(){
		return new Vector2D(x, y);
	}
}
