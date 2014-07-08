package org.jack.app.kml;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Point;

public class KmlTest {

	/*
	 * 根据name 经度 纬度 生成kml文件
	 */
	public static void getPointKml(String name, double lon, double lat)
			throws FileNotFoundException {
		final Kml kml = new Kml();
		kml.createAndSetPlacemark().withName(name).withOpen(Boolean.TRUE)
				.createAndSetPoint().addToCoordinates(lon, lat);
		kml.marshal();
		kml.marshal(new File("D:\\" + name + ".kml"));
	}

	/*
	 * 解析point的kml文件 打印出经纬度高度
	 */
	public static void getKmlPoint(String kmlName) {
		final Kml kml = Kml.unmarshal(new File(kmlName + ".kml"));
		final Placemark placemark = (Placemark) kml.getFeature();
		Point point = (Point) placemark.getGeometry();
		List<Coordinate> coordinates = point.getCoordinates();
		for (Coordinate coordinate : coordinates) {
			System.out.println(coordinate.getLatitude());
			System.out.println(coordinate.getLongitude());
			System.out.println(coordinate.getAltitude());
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		// getPointKml("Hangzhou",120,30);
		getKmlPoint("D:\\kml");
	}
}
