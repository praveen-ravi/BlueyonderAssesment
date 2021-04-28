#BlueYonderAssesment


KafkaConfiguration

To modify kafka configuration edit src/main/resources/kafka.properties
broker configuration and topic name can be edited here. Also includes the aggregate configuration for kafka producer and consumer


ProjectConfiguration
To modify consumer output location and poll duration edit src/main/resources/project.properties


Endpoints


POST /customers
requestBody:
{
	"firstName": <fname>,
	"lastName" : <lname>,
	"emailAddrest" : <emailId>
}
Description: Pushes a customer record into the kafka queue

GET /startConsumer

Description: Starts the kafka consumer and starts reading the customer records into the file configured in project.properties

GET /stopConsumer

Description: Stops the kafka consumer
