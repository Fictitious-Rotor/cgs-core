function writeSetToBundle(subPluginName, sessionHandlerArray, storedHandlerArray) {
    return "        subPluginsToMetadataHandlers.put(\"" + subPluginName + "\", " + writeBundle(subPluginName, sessionHandlerArray, storedHandlerArray) + ");";
}

function writeBundle(subPluginName, sessionHandlerArray, storedHandlerArray) {
    return "new MetadataBundle(" + treatSessionHandlerArray(sessionHandlerArray) + treatStoredHandlerArray(subPluginName, storedHandlerArray) + ")";
}

function treatSessionHandlerArray(givenArray) {
    if (givenArray.length === 0) { return ''; }

    return "new " + givenArray.join("(), new ") + "(), ";
}

function treatStoredHandlerArray(subPluginName, givenArray) {
    const methodCallStr = "(\"" + subPluginName + "\")";

    return "new " + givenArray.join(methodCallStr + ", new ") + methodCallStr;
}

function getJavaClassReference(givenFileLocation) {
    return givenFileLocation.split('/')
                            .slice(4)
                            .join('.');
}

function getReferenceArray(givenString) {
    if (givenString === "<None>") { return []; }

    return givenString.split(';')
                      .map(getJavaClassReference)
                      .filter(Boolean);
}

const args = process.argv;

const subPluginName = args[2];
const sessionHandlerArray = getReferenceArray(args[3]);
const storedHandlerArray = getReferenceArray(args[4]);

console.log("        // Handlers");
console.log(writeSetToBundle(subPluginName, sessionHandlerArray, storedHandlerArray));