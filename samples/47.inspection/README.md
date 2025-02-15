# Inspection Bot

This bot demonstrates a feature called Inspection. This feature allows the Bot Framework Emulator to debug traffic into and out of the bot in addition to looking at the current state of the bot. This is done by having this data sent to the emulator using trace messages.

This bot has been created using [Bot Framework](https://dev.botframework.com), it shows how to create a simple bot that accepts input from the user and echoes it back. Included in this sample are two counters maintained in User and Conversation state to demonstrate the ability to look at state.

This runtime behavior is achieved by simply adding a middleware to the bot. In this sample you can find that being done in the `AdapterWithInspection` class.

More details are available [here](https://github.com/microsoft/BotFramework-Emulator/blob/master/content/CHANNELS.md)

This sample is a Spring Boot app and uses the Azure CLI and azure-webapp Maven plugin to deploy to Azure.

## Prerequisites

- Java 1.8+
- Install [Maven](https://maven.apache.org/)
- An account on [Azure](https://azure.microsoft.com) if you want to deploy to Azure.

## To try this sample locally
- From the root of this project folder:
  - Build the sample using `mvn package`
  - Run it by using `java -jar .\target\bot-inspection-sample.jar`

- Test the bot using Bot Framework Emulator

  [Bot Framework Emulator](https://github.com/microsoft/botframework-emulator) is a desktop application that allows bot developers to test and debug their bots on localhost or running remotely through a tunnel.

  - Install the Bot Framework Emulator version 4.3.0 or greater from [here](https://github.com/Microsoft/BotFramework-Emulator/releases)

  - Connect to the bot using Bot Framework Emulator

    - Launch Bot Framework Emulator
    - File -> Open Bot
    - Enter a Bot URL of `http://localhost:8080/api/messages`

### Special Instructions for Running Inspection

- In the emulator, select Debug -> Start Debugging.
- Enter the endpoint url (http://localhost:8080)/api/messages, and select Connect.
- The result is a trace activity which contains a statement that looks like /INSPECT attach < identifier >
- Right click and copy that response.
- In the original Live Chat session paste the value.
- Now all the traffic will be replicated (as trace activities) to the Emulator Debug tab.

## Deploy the bot to Azure

As described on [Deploy your bot](https://docs.microsoft.com/en-us/azure/bot-service/bot-builder-deploy-az-cli), you will perform the first 4 steps to setup the Azure app, then deploy the code using the azure-webapp Maven plugin.

### 1. Login to Azure
From a command (or Powershell) prompt in the root of the bot folder, execute:  
`az login`
  
### 2. Set the subscription
`az account set --subscription "<azure-subscription>"`

If you aren't sure which subscription to use for deploying the bot, you can view the list of subscriptions for your account by using `az account list` command. 

### 3. Create an App registration
`az ad app create --display-name "<botname>" --password "<appsecret>" --available-to-other-tenants`

Replace `<botname>` and `<appsecret>` with your own values.

`<botname>` is the unique name of your bot.  
`<appsecret>` is a minimum 16 character password for your bot. 

Record the `appid` from the returned JSON

### 4. Create the Azure resources
Replace the values for `<appid>`, `<appsecret>`, `<botname>`, and `<groupname>` in the following commands:

#### To a new Resource Group
`az deployment create --name "echoBotDeploy" --location "westus" --template-file ".\deploymentTemplates\template-with-new-rg.json" --parameters groupName="<groupname>" botId="<botname>" appId="<appid>" appSecret="<appsecret>"`

#### To an existing Resource Group
`az group deployment create --name "echoBotDeploy" --resource-group "<groupname>" --template-file ".\deploymentTemplates\template-with-preexisting-rg.json" --parameters botId="<botname>" appId="<appid>" appSecret="<appsecret>"`

### 5. Update the pom.xml
In pom.xml update the following nodes under azure-webapp-maven-plugin
- `resourceGroup` using the `<groupname>` used above
- `appName` using the `<botname>` used above

### 6. Update app id and password
In src/main/resources/application.properties update 
  - `MicrosoftAppPassword` with the botsecret value
  - `MicrosoftAppId` with the appid from the first step

### 7. Deploy the code
- Execute `mvn clean package` 
- Execute `mvn azure-webapp:deploy`

If the deployment is successful, you will be able to test it via "Test in Web Chat" from the Azure Portal using the "Bot Channel Registration" for the bot.

After the bot is deployed, you only need to execute #7 if you make changes to the bot.


## Further reading

- [Maven Plugin for Azure App Service](https://docs.microsoft.com/en-us/java/api/overview/azure/maven/azure-webapp-maven-plugin/readme?view=azure-java-stable)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Azure for Java cloud developers](https://docs.microsoft.com/en-us/azure/java/?view=azure-java-stable)
- [Bot Framework Documentation](https://docs.botframework.com)
- [Bot Basics](https://docs.microsoft.com/azure/bot-service/bot-builder-basics?view=azure-bot-service-4.0)
- [Activity processing](https://docs.microsoft.com/en-us/azure/bot-service/bot-builder-concept-activity-processing?view=azure-bot-service-4.0)
- [Azure Bot Service Introduction](https://docs.microsoft.com/azure/bot-service/bot-service-overview-introduction?view=azure-bot-service-4.0)
- [Azure Bot Service Documentation](https://docs.microsoft.com/azure/bot-service/?view=azure-bot-service-4.0)
