function constructSubPlugin(subPluginName) {
    return "        allSubPlugins.add(new SubPlugin(\"" + subPluginName + "\"," + getMH(subPluginName) + ", " + getListeners(subPluginName) + "));";
}

function getMH(subPluginName) {
    return "subPluginsToMetadataHandlers.get(\"" + subPluginName + "\")";
}

function getListeners(subPluginName) {
    return "subPluginsToListeners.get(\"" + subPluginName + "\")";
}

const args = process.argv;

const subPluginName = args[2];
//const configLocation = args[3];

console.log("        // SubPlugin");
console.log(constructSubPlugin(subPluginName));
console.log("        ");