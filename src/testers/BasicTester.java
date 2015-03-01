package testers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BasicTester {
	protected Object valueExpected = 1;
	protected Object valueActual = 0;

	public void test() {
		assertEquals(valueExpected, valueActual);
		valueActual = 0;
		valueExpected = 1;
	}

	public Object getValueExpected() {
		return valueExpected;
	}

	public void setValueExpected(Object valueExpected) {
		this.valueExpected = valueExpected;
	}

	public Object getValueActual() {
		return valueActual;
	}

	public void setValueActual(Object valueActual) {
		this.valueActual = valueActual;
	}
}
