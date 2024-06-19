package com.al.basic.leaf.test;

import com.al.basic.leaf.service.LeafService;
import com.al.basic.leaf.starter.LeafAutoConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

@SpringBootApplication
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LeafAutoConfiguration.class)
public class LeafServerApplicationTests {

	@Inject
	private LeafService leafService;

	@Test
	public void test() {
		long st = System.currentTimeMillis();

		long times = 5;
		for(int i=0; i<times; i++) {
			System.out.println(leafService.genId("order_service.t_order"));
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		long et = (System.currentTimeMillis());
		System.out.println((et - st) + ", tps:" + times /((et -st)/1000.0));
	}

}
