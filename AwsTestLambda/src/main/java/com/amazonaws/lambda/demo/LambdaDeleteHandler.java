package com.amazonaws.lambda.demo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.kinesisfirehose.model.ResourceNotFoundException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaDeleteHandler implements RequestHandler<Todo, String> {

	@Override
	public String handleRequest(Todo input, Context context) {
		context.getLogger().log("Input for get: " + input);

		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
		DynamoDBMapper mapper = new DynamoDBMapper(client);
		Todo todo = null;
		todo = mapper.load(Todo.class, input.getTodoId());
		if (todo == null) {
			throw new ResourceNotFoundException("Not Found : " + input.getTodoId());
		} else {
			mapper.delete(todo);
			return "success";
		}
	}

}
