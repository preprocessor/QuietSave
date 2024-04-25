# QuietSave

A world backup plugin for BTA servers.

This plugin will automatically zip your server's world into a specified directory every so often (20 minutes by default)

You can also manually backup the world.

### Config options

- `saveFrequency`: How often backups are saved, in minutes.

- `saveDir`: Directory in the root of the minecraft server to save backups to. This will be created if it does not exist.

### Commands

- `backup`: Saves the current world to the backups directory
