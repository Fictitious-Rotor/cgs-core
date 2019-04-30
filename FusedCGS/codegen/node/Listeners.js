function writeSetToMap(subPluginName, setContentsArray) {
    return "        subPluginsToListeners.put(\"" + subPluginName + "\", " + writeSet(subPluginName, setContentsArray) + ");";
}

function writeSet(subPluginName, givenArray) {
    const methodCallStr = "(subPluginsToMetadataHandlers.get(\"" + subPluginName + "\"), new PlayerInterface(\"" + subPluginName + "\"))";
    return "ImmutableSet.of(new " + givenArray.join(methodCallStr + ", new ") + methodCallStr + ")";
}

function getJavaClassReference(givenFileLocation) {
    return givenFileLocation.split('/')
                            .slice(4)
                            .join('.');
}

function getReferenceArray(givenString) {
    return givenString.split(';')
                      .map(getJavaClassReference)
                      .filter(Boolean);
}

const args = process.argv;

const subPluginName = args[2];
const listenerArray = getReferenceArray(args[3]);

console.log("        // Listeners");
console.log(writeSetToMap(subPluginName, listenerArray));