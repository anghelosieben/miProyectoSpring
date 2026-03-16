# Carpeta `arquitectura`

Contiene el diagrama de arquitectura del proyecto en formato Mermaid.

- `diagrama.mmd`: diagrama Mermaid (fuente).  
- `diagrama.md`: Markdown con bloque Mermaid para previsualizar en GitHub/VSCode.

Opciones para exportar a imagen:

1) Usando `npx @mermaid-js/mermaid-cli`:

```bash
npx @mermaid-js/mermaid-cli -i arquitectura/diagrama.mmd -o arquitectura/diagrama.png
npx @mermaid-js/mermaid-cli -i arquitectura/diagrama.mmd -o arquitectura/diagrama.svg
```

2) En VS Code: instala la extensión "Markdown Preview Mermaid" o "vscode-mermaid-preview" y abre `diagrama.md`.
