/** Removove whitespaces, comments and gas specific prefixes and symbols */
def clean(line: String) = {
  val trimmed = line.trim.filter(!Array('$', '%').contains(_)).replaceAll("\\(", "\\[").replaceAll("\\)", "\\]").replaceAll("\\/\\/", "#")
  if trimmed.contains("#") then
    trimmed.slice(0, trimmed.indexOf("#"))
  else
    trimmed
}

/** Return comment or empty string from a line */
def get_comment(line: String) = {
  var unified = line.replaceAll("\\/\\/", "#")
  if unified.contains("#") then
    ";" + unified.substring(unified.indexOf("#")+1, unified.length)
  else
    ""
}

/** Indent line unless it is a laber */
def indent(line: String) = 
  if !line.contains(":") then
    "    "+line
  else
    line

/** Conversion for a single line */
def convert2intel(line: String) = { 
  val trimmed = clean(line)

  if trimmed.length != 0 && trimmed.contains(',') && !trimmed.contains('"') && trimmed(0) != '.' then
    val tokens = trimmed.replaceAll(" +,", ",").replaceAll(", +", ",").split("((?<=[,\\s])|(?=[,\\s]))")
    val rev = tokens.slice(tokens.indexOf(",")-1, tokens.lastIndexOf(",")+2).reverse.mkString.replaceAll(",", ", ")
    indent(tokens.slice(0, tokens.indexOf(",")-2).mkString + " "
        + rev.mkString
        + tokens.slice(tokens.lastIndexOf(",")+2, tokens.length-1)
      .mkString).mkString + get_comment(line)
  else
    indent(trimmed).mkString + get_comment(line)
}

/** Read file line by line */
def lineStream(path: String) = scala.io.Source.fromFile(path).getLines()

/** 
 * Converts gas syntax assembly to intel syntax 
 * Usage: att2intel code.s
 */
@main def att2intel(arg1: String) = 
  if arg1 == "-h" || arg1 == "-help" then
    println("att2intel - AT&T syntax assembly converter to Intel syntax\nUsage: att2intel <assembly file>")
  else
    for l <- lineStream(arg1) do println(convert2intel(l))
