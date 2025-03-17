Keep Your Minecraft Server Smooth! 🚀

This mod automatically cleans up entities on your Minecraft server to prevent lag. It does this at regular intervals that you can configure. You can also enable/disable and reset the timer with a command.

**⚙️ How does it work?**

The "Custom Clear Lag" mod works in the background on your server. Here are the key steps:

1. **📝 Configuration:** When the server starts for the first time with the mod installed, a configuration file named `clear_lag.json` is created in the `config/oas_work/` folder.
2. **📖 Reading the configuration file:** The mod reads this `clear_lag.json` file to find out:
    * **⏱️ `time`:** After how many minutes the cleanup should be performed (default is **5 minutes**).
    * **💥 `command`:** Which command should be executed for the cleanup (default is `kill @e` which kills all entities, **be careful!**).
3. **⏳ Countdown:** The mod counts the time elapsed in the game.
4. **🧹 Automatic Cleanup:** Once the configured time has elapsed, the mod executes the command defined in the configuration file. After the cleanup, the time counter is reset and starts again.

**🎮 How to use it?**

1. **🛠️ Configuration (optional but **recommended**):**
    * **📂 Locate the configuration file:** Go to the `config/oas_work/` folder of your server and open the `clear_lag.json` file with a text editor.
    * **✏️ Modify the settings:**
        * **`"time": 5,`** Change `5` to the desired number of minutes between each cleanup (e.g., `10` for **10 minutes**).
        * **`"command": "kill @e"`**  **⚠️ VERY IMPORTANT:** The default command `kill @e` kills ***ALL*** entities (items on the ground, animals, players...). **You MUST change it to a more targeted cleanup command.** For example:
            * `"/kill @e[type=!minecraft:player,distance=500]"` (Kills all entities except players, more than 500 blocks away - ***adapt to your server***).
            * `/minecraft:kill @e[type=!player,tag=!keep]` (Requires tags to protect certain entities, more advanced).
    * **💾 Save** the `clear_lag.json` file. *🔄 **Changes are applied immediately, no server restart needed!***

2. **🕹️ In-game commands (for server operators):**

   Open the server console or the in-game chat (if you are an operator) and use the command: `/cclearlag`

   * **`/cclearlag stop <true/false>`** :
     * `/cclearlag stop true` **🛑 Stops** automatic cleanup.
     * `/cclearlag stop false` **✅ Reactivates** automatic cleanup.

   * **`/cclearlag reset <true/false>` or `/cclearlag reset`** :
     *  **🔄 Resets the timer.** The countdown to the next cleanup will restart from zero. The `true/false` argument has no effect here, only `reset` matters.

**📌 Important Points:**

* **🌐 Hosting Compatibility:** This mod works on all types of Minecraft Forge/NeoForge servers.
* **🔑 Permissions:** Only server operators (level 4) can use the `/cclearlag` command.
* **⚠️ Essential Configuration:** ***Absolutely change the default `kill @e` command in `clear_lag.json`!*** Use a more suitable command for your server to avoid deleting important entities (players, tamed animals, etc.).
* **🔄 Instant Changes:** Modifications to the `clear_lag.json` file are applied **immediately**, without restarting the server.
