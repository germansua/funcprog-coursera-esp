// val f: String => String = { case "ping" => "pong" }

val f: PartialFunction[String, String] = { case "ping" => "pong" }
f("ping")
f.isDefinedAt("abc")