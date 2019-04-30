function writeSetToMap(subPluginName, setContentsArray) {
    return "        subPluginsToCommandBackends.put(\"" + subPluginName + "\", " + writeSet(setContentsArray) + ");";
}

function writeSet(givenArray) {
    return "ImmutableSet.of(new " + givenArray.join("(), new ") + "())";
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
const commandBackendArray = getReferenceArray(args[3]);

console.log("        // Command Backends");
console.log(writeSetToMap(subPluginName, commandBackendArray));