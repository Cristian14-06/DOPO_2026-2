# Etiquetas de excepciones en los diagramas de secuencia

Guía para poner las etiquetas en los 4 diagramas de secuencia del laboratorio 6
(`optionOpen`, `optionSaveAs`, `optionImport`, `optionExportAs`).

## Convención

| Letra | Significado | Cuándo se usa |
|---|---|---|
| **L** | Lanzar | El método crea y lanza la excepción (`throw new ...`). |
| **P** | Propagar | El método no atrapa una excepción que le llega de otro y la deja pasar hacia quien lo llamó. |
| **A** | Atrapar | El método tiene un `catch` para esa excepción. |

## Dónde se ponen

1. **En la línea del llamado de cada método:** siempre va la etiqueta con todo lo que ese método hace con las excepciones (`L`, `P` y/o `A`, según corresponda). Si tiene uno o varios `catch`, se pone `A: ...` con las excepciones que atrapa.
2. **Dentro de cada condicional donde se lanza o propaga algo:** se pone la etiqueta `L: ...` o `P: ...` dentro de esa rama.

Formato: `A: Excepcion1, Excepcion2` · `L: Excepcion` · `P: Excepcion`. Si un llamado lleva varias, van juntas en la misma etiqueta, por ejemplo `A: IOException | P: EcoSafariException`.

---

## optionOpen

**Línea `optionOpen()` (Usuario → EcoSafariGUI)**
- `A: EcoSafariException`

**Línea `open(file)` (EcoSafariGUI → EcoSafari)**
- `A: IOException, ClassNotFoundException`
- `P: EcoSafariException`

**Dentro de los condicionales de `open`**, en cada rama que crea la excepción:
- `[file == null]` → `L: EcoSafariException`
- `[!file.exists()]` → `L: EcoSafariException`
- `[file.isDirectory()]` → `L: EcoSafariException`
- `[!file.getName().endsWith(".dat")]` → `L: EcoSafariException`
- `[IOException | ClassNotFoundException]` → `L: EcoSafariException`

---

## optionSaveAs

**Línea `optionSaveAs()` (Usuario → EcoSafariGUI)**
- `A: EcoSafariException`

**Línea `saveAs(file)` (EcoSafariGUI → EcoSafari)**
- `A: IOException`
- `P: EcoSafariException`

**Dentro de los condicionales de `saveAs`:**
- `[file == null]` → `L: EcoSafariException`
- `[file.exists() && file.isDirectory()]` → `L: EcoSafariException`
- `[!file.getName().endsWith(".dat")]` → `L: EcoSafariException`
- `[IOException]` → `L: EcoSafariException`

---

## optionExportAs

**Línea `optionExportAs()` (Usuario → EcoSafariGUI)**
- `A: EcoSafariException`

**Línea `exportAs(file)` (EcoSafariGUI → EcoSafari)**
- `A: IOException`
- `P: EcoSafariException`

**Dentro de los condicionales de `exportAs`:**
- `[file == null]` → `L: EcoSafariException`
- `[file.exists() && file.isDirectory()]` → `L: EcoSafariException`
- `[!file.getName().endsWith(".txt")]` → `L: EcoSafariException`
- `[IOException]` → `L: EcoSafariException`

---

## optionImport

**Línea `optionImport()` (Usuario → EcoSafariGUI)**
- `A: EcoSafariException`

**Línea `import_(file)` (EcoSafariGUI → EcoSafari)**
- `A: IOException, NumberFormatException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException`
- `P: EcoSafariException`

**Dentro de los condicionales de `import_`:**
- `[file == null]` → `L: EcoSafariException`
- `[!file.exists()]` → `L: EcoSafariException`
- `[file.isDirectory()]` → `L: EcoSafariException`
- `[!file.getName().endsWith(".txt")]` → `L: EcoSafariException`
- `[particion.length < 2]` → `L: EcoSafariException`
- `[posiciones.length < 2]` → `L: EcoSafariException`
- `[fila no numérica]` → `L: EcoSafariException` (viene de atrapar `NumberFormatException`)
- `[columna no numérica]` → `L: EcoSafariException` (viene de atrapar `NumberFormatException`)
- `[!isInside(i, j)]` → `L: EcoSafariException`
- `[celda ocupada]` → `L: EcoSafariException`
- `[ClassNotFoundException]` → `L: EcoSafariException`
- `[error en el constructor]` → `L: EcoSafariException`
- `[IOException]` → `L: EcoSafariException`

---

## Llamados sin etiqueta

Estos métodos no lanzan, propagan ni atrapan excepciones, así que su línea no lleva etiqueta:
`isInside(i, j)`, `set(this, i, j)`, el `<<create>>` de la entidad, `paintComponent(g)`, `getTheEcoSafari()`, `getSize()`, `get(r, c)`, `getClass().getSimpleName()`.

## Resumen del recorrido de la excepción

En los 4 diagramas la `EcoSafariException` hace siempre el mismo camino:

1. **L** dentro del condicional del modelo, donde se hace `throw new EcoSafariException(...)`.
2. **P** en la línea de llamado del método del modelo (`open`, `saveAs`, `exportAs`, `import_`), porque no la atrapa y sale hacia la GUI.
3. **A** en la línea de llamado del método de la GUI (`optionOpen`, `optionSaveAs`, `optionExportAs`, `optionImport`), que la atrapa y muestra el mensaje.

Las excepciones de Java (`IOException`, `NumberFormatException`, etc.) solo llevan **A**, en la línea del método del modelo que las atrapa.

---

# Qué revisar en `Laboratorio6.tex`

Antes de compilar el PDF, revisa y completa esto:

1. **Retrospectiva 1, horas:** poner las horas de cada uno donde dice *[completar]*.
2. **Retrospectiva 3, práctica XP más útil:** la práctica debe ser una de las dos que establece el enunciado del laboratorio, vistas en clase:
   - *Coding: Only one pair integrates code at a time.*
   - *Coding: Use collective ownership.*

   Quedó escrita *propiedad colectiva del código (collective ownership)*. Confirma que es la que consideramos más útil y ajusta el porqué si hace falta.
3. **Retrospectiva 4, 5 y 6 (logro, problema técnico y trabajo en equipo):** son opiniones del equipo; confirma que reflejan lo que pasó.
4. **Retrospectiva 7 y bibliografía:** dejar solo las referencias que de verdad usamos y agregar las que falten (por ejemplo, material de clase).
5. **Parte I.A.1, ambiente de trabajo:** confirmar que la respuesta (BlueJ al inicio, luego VS Code) es correcta.
