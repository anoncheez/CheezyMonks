package core;

import java.util.Random;

public class RandomGenerator {
	public static RandomGenerator instance = null;
	private static Random random = null;
	
	protected RandomGenerator(){
		random = new Random();
	}
	
	public static RandomGenerator getInstance(){
		if(instance == null){
			instance = new RandomGenerator();
		}
		return instance;
	}
	
	public Random getGenerator(){
		return random;
	}
}
