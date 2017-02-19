for (b <- books; a <- b.authors if a startsWith "Birds") yield b.title

books.flatMap(b =>
  for (a <- b.authors if a startsWith "Birds") yield b.title
)

books.flatMap(b =>
  for (a <- b.authors withFilter (a startsWith "Birds")) yield b.title
)

books.flatMap(
  b.authors => map(a.withFilter(a startsWith "Birds") => b.title)
)


/** *****************************************************************************/
for (b <- books; a <- b.authors if a startsWith "Birds") yield b.title

books.flatMap(b =>
  for (a <- b.authors if a startsWith "Birds") yield b.title
)

books.flatMap(b =>
  for (a <- b.authors.withFilter(a => a.startsWith("Birds"))) yield b.title
)

books.flatMap(b =>
  b.authors.withFilter(a => a.startsWith("Birds")).map(y => y.title)
)
