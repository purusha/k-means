package it.si.unimi;

import java.awt.Point;

import junit.framework.Assert;

import org.junit.Test;

public class PointTest {
	
	@Test
	public void simpleUsage() {
		Point a = new Point(3, 3);

		Assert.assertEquals(3, a.x);
		Assert.assertEquals(3d, a.getX());
		
		Assert.assertEquals(3, a.y);
		Assert.assertEquals(3d, a.getY());
	}

}
