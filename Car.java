import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Class @author: Carlos Rodriguez
 * Class holds information about a car */
public class Car {

	/** Name = label to distinguish the car, Path = the path the car will travel*/
	private String name, path;
	/** Color = the color to distinguish the car */
	private Color color;
	/** Wheels = the wheels for the car, Engine = the selected engine,
	 *  Speed = the registered speed */
	private int wheels, engine, speed;
	/** carBody = Circle object that represents the car, will be used in Race class */
	private Circle carBody;

	//Default Constructor
	public Car() {
		this.name = this.path = null;
		this.carBody = null;
		this.color = null;
		this.wheels = this.engine = this.speed = 0;
	}

	//Passing a car's information
	public Car(String name, String path, Color color, int wheels, int engine) {
		this.name = name;
		this.path = path;
		this.color = color;
		this.wheels = wheels;
		this.engine = engine;
		this.speed = this.wheels + this.engine;
		this.carBody = new Circle(25, this.color);
	}

	//Accessors
	public String getName() {
		return this.name;
	}

	public String getPath() {
		return this.path;
	}

	public int getSpeed() {
		return this.speed;
	}

	public Circle getCarBody() {
		return this.carBody;
	}

	//toString
	@Override
	public String toString() {
		return "Car info:\nName: " + this.name + "\nPath: " + this.path +
				"\nColor:" + this.color + "Wheels(speed): " + this.wheels +
				"\nEngine(speed): " + this.engine + "\nTotal speed: " + this.speed;
	}

	//equals
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(obj == this) return true;
		if(obj.getClass() == this.getClass()) {
			Car aCar = (Car) obj;
			return this.name.equals(aCar.name) && this.path.equals(aCar.path) &&
					this.color.equals(aCar.color) && (this.wheels == aCar.wheels) &&
					(this.engine == aCar.engine) && (this.speed == aCar.speed) &&
					this.carBody.equals(aCar.carBody);
		}else {
			return false;
		}
	}

}