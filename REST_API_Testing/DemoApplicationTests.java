package com.example.demo;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import javax.ws.rs.core.UriBuilder;
import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
class DemoApplicationTests {

	private ArrayList<Coupon> get_m_coupons(CouponController coupon) {
		try {
			ArrayList<Coupon> m_coupons = null;
			Field privateStringField = null;
			privateStringField = CouponController.class.
					getDeclaredField("m_coupons");
			privateStringField.setAccessible(true);
			m_coupons = (ArrayList<Coupon>) privateStringField.get(coupon);

			return m_coupons;
		}
		catch (NoSuchFieldException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Test
	void contextLoads() {
	}

	@Test
	public void test_get_all_jersey() {

		CouponController couponController = new CouponController();
		ArrayList<Coupon> m_coupons = get_m_coupons(couponController);

		ClientConfig clientConfig = new DefaultClientConfig();
		Client client = Client.create(clientConfig);
		WebResource webResource =
				client.resource(UriBuilder.fromUri("http://localhost:8080/coupon").  build());
		String result =  webResource.path("").path("").accept(String.valueOf(MediaType.APPLICATION_JSON)).get(String.class);
		System.out.println(result);

		Gson gson = new Gson();
		JsonArray ja = JsonParser.parseString(result).getAsJsonArray();
		Coupon[] coupons = gson.fromJson(ja, Coupon[].class);

		for (int i = 0; i < m_coupons.size(); i++){
			assertEquals(coupons[i].getId(), m_coupons.get(i).getId() );
			assertEquals(coupons[i].getTitle(), m_coupons.get(i).getTitle() );
		}
		assertEquals(coupons.length, m_coupons.size());
	}

	@Test
	public void test_get_by_1_jersey() {

		CouponController couponController = new CouponController();
		ArrayList<Coupon> m_coupons = get_m_coupons(couponController);

		ClientConfig clientConfig = new DefaultClientConfig();
		Client client = Client.create(clientConfig);
		WebResource webResource =
				client.resource(UriBuilder.fromUri("http://localhost:8080/coupon/1").  build());
		String result =  webResource.path("").accept(String.valueOf(MediaType.APPLICATION_JSON)).get(String.class);
		System.out.println(result);

		Gson gson = new Gson();
		JsonObject jo = JsonParser.parseString(result).getAsJsonObject();
		Coupon coupon = gson.fromJson(jo, Coupon.class);

		assertEquals(m_coupons.get(0).getId(), coupon.getId());
		assertEquals(m_coupons.get(0).getTitle(), coupon.getTitle());
	}
}
