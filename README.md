# Vixio

The Skript Addon made to fit all of your Discord needs. 

# Installing

1. Download the latest version of [Skript](https://github.com/bensku/Skript/releases)
2. Download the latest version of [Vixio](https://github.com/iBlitzkriegi/Vixio/releases)
3. Place both jars in your plugins folder
4. Restart your server

# Setup

To use Vixio, you must first have a bot on your Discord server. To do so, follow the [picture tutorial](https://imgur.com/a/RwNN4) or the instructions below:

- Go to [discord's application dashboard](https://discordapp.com/developers/applications/me) and click on "New Application". You may have to login!
- Give the application a name under "App Name" then in the bottom right click "Create Application".
- You should now see your new applicaion. Next, under "App Details" click on "Create a bot user".
- Click "Yes, do it" on the conformation screen.
- Next, under "App Bot User" find "Token" and click "Click to reveal" and copy the token.
    - Remember to save that token, that's what you will use on startup with Vixio.
- Find the "Client ID" at the top of the page.
- Replace the X's with the client ID in this URL: https://discordapp.com/oauth2/authorize?client_id=XXXXXXXXX&scope=bot&permissions=0 
    - You can give that link to anyone so they can add it to their guild. 

# Usage

Documentation for Vixio can be found at `plugins/Vixio/Syntaxes.txt` after Vixio loads for the first time.

# Compiling

Vixio uses Gradle for compilation.

### Linux
```
./gradlew clean build
```

### Windows
```
gradlew.bat clean build
```



