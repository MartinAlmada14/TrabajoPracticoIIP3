package Model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;



@RunWith(Suite.class)
@SuiteClasses({
	AristaTest.class,
	VerticeTest.class,
	ResultadoRegionesTest.class,
	GrafoTest.class,
	UnionFindTest.class,
	KruskalTest.class,
	ArbolGeneradorMinimoTest.class,
	PaisTest.class,

})
public class AllTest {
}