# MAL-Music-Bot
Discord Bot that allows you to play MAL music trivia with friends.

### How to Install
You will need Gradle to run the Bot. Clone this repository, and run the following Gradle command (I use Intellij to open and run the project):
```
gradle clean jar
```
This will build the .jar file into the `target` folder. Before running the .jar file, you will need to create a file called `bot.yml` and place it in the same directory as the .jar file. `bot.yml` should contain the following lines:
```
discord:
  token: <BOT_TOKEN>

mal:
  url: https://api.myanimelist.net/v2
  clientId: <CLIENT_ID>
  pageLimit: 1000

animethemes:
  url: https://api.animethemes.moe
  pageLimit: 100

jikan:
  url: https://api.jikan.moe/v4

voiceLines:
 - Example voice line.
 - ...
```
Replace `<BOT_TOKEN>` with the token obtained from your own Discord Bot (More details on creating a Discord Bot can be found [here](https://discord.com/developers/docs/intro)).

Replace `<CLIENT_ID>` with a client id you own from MyAnimeList's API services (More details can be found [here](https://myanimelist.net/apiconfig/references/api/v2#section/Authentication)).

Voice lines are used as filler text while the bot is picking a song. You may use as many as you want, and they will be selected at random when needed.

Once this is set up, you can run the .jar file using the following command:
```
java -jar MAL-Music-Bot-2-1.0-SNAPSHOT.jar
```
Once the Bot is running, you can invite it to your Discord Server from the [Discord Developer Portal](https://discord.com/developers/applications) and interact with it from your text channels.

### Usage
Tag the bot or type !iwa and send one of the following messages into a text channel to use the bot:

`/adduser <username>`

Add a MyAnimeList user's library to the song list

`/removeuser <username>`

Remove a MyAnimeList user's library from the song list

`/listusers`

List all MyAnimeList users who are currently part of the song list

`/play`

Play a random anime theme based on the current users and settings

`/stop`

Stop the currently playing anime theme

`/settings`

Adjust the bot settings
