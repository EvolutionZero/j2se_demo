package com.zero.j2se.demo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Builder
@RequiredArgsConstructor(staticName="of")
@AllArgsConstructor
public class LearnLombok {

	@NonNull
	private String name;
	@NonNull
	private Integer age;
	@NonNull
	private Integer sex;
	
	private String phone;
	
	private String address;
	
	public static void main(String[] args) {
		// @Builder加@AllArgsConstructor注解，使用建造者模式
		LearnLombok build = LearnLombok.builder().name("张三").age(19).sex(1).build();
		
		// @NonNull和@RequiredArgsConstructor(staticName="of")注解，链式设置属性
		LearnLombok ofPerson = LearnLombok.of("张三", 19, 1).setAddress("广州");
	}
	
}
