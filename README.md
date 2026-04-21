<div align="center">
  <h1>Práctica 3: Práctica 3.- Uso de la Herramienta JFLAP para Convertir Autómatas Finitos Deterministas a Expresiones Regulares y Extensión de Software para simular Autómatas No Deterministas, AFN con Transiciones Lambda y Minimización de AFD</h1>
   <p><i>Juárez Velázquez Erick Daniel 2025630052</i></p>
   <p><i>Placencia Murguia Juan Emilio 2025630024</i></p>
</div>


## Introducción 
Este repositorio alberga el código fuente y la documentación correspondientes a la Práctica 3 de la unidad de aprendizaje de Teoría de la Computaciónl. El propósito de esta práctica es profundizar en la estrecha relación matemática y funcional que existe entre los autómatas finitos y las expresiones regulares, empleando para el análisis previo la herramienta JFLAP. En cuanto a la implementación de software, este proyecto extiende la aplicación con interfaz gráfica en Java Swing desarrollada previamente para incluir la simulación paso a paso de tres formalismos: 
* *Autómatas Finitos Deterministas (AFD)* 
* * *Autómatas Finitos No Deterministas (AFND)* 
* * *Autómatas Finitos No Deterministas con Transiciones Lambda (AFN-λ)* 

Adicionalmente, el programa incorpora un módulo avanzado de optimización que otorga la capacidad de minimizar cualquier AFD dado, reduciendo su número de estados al mínimo equivalente mediante el algoritmo de clases de equivalencia.

---

##  Objetivos

### Objetivo General
Comprender, analizar e implementar computacionalmente los algoritmos fundamentales para la manipulación, transformación y optimización de Autómatas Finitos, demostrando la equivalencia entre distintas representaciones de lenguajes regulares a través del uso de herramientas académicas especializadas y el desarrollo de software orientado a objetos.

## Parte 1: Conversión de Autómatas a Expresiones Regulares con JFLAP

La primera parte de esta práctica tuvo un enfoque analítico y demostrativo centrado en la conversión de Autómatas Finitos Deterministas (AFD) a expresiones regulares. 

Dado que este proceso se realizó de forma manual y con el apoyo de la herramienta *JFLAP*, el desarrollo de esta sección no se encuentra dentro del código fuente de la aplicación Java. En su lugar, toda la evidencia de esta etapa está disponible en este repositorio a través de dos archivos principales:

* *Documento PDF:* Contiene el reporte teórico detallado. Aquí se documenta el proceso paso a paso de las conversiones, la comprobación de equivalencia con cadenas de prueba y la investigación comparativa de los distintos métodos de conversión.
* *Archivo ZIP:* Contiene los archivos fuente originales de los ejercicios. Dentro de esta carpeta comprimida se encuentran los modelos de los autómatas seleccionados (en formato .jff), listos para ser extraídos y abiertos directamente en el software JFLAP para su revisión y validación.

## Instrucciones de Compilación y Ejecución

Para garantizar el correcto funcionamiento de la aplicación y evitar conflictos de configuración, se recomienda ejecutar el proyecto directamente desde la terminal del sistema operativo. Aunque el código puede abrirse en entornos de desarrollo, el uso de IDEs como NetBeans podría generar errores inesperados en la carga de dependencias o recursos.

### Requisitos Previos

* *Java JDK/JRE:* Asegúrate de tener instalada la versión 25 o superior. 

### Ejecución desde la Terminal

1. Abre la terminal o consola de comandos abre la carpeta Proyecto
2. Una vez dentro de la carpeta Proyecto se debe de seguir lo siguiente
* cd Lenguajes
* cd Lenguajes
* cd target
4. Por último, ejecuta la aplicación utilizando el siguiente comando:

 bash
java -jar Lenguajes-1.0-SNAPSHOT.jar 


##  Extensión de la Aplicación para Simulación de Autómatas No Deterministas

### 2.1 Simulación de Autómatas No Deterministas (AFND)

#### Definición y Representación
Para escalar la aplicación del modelo determinista (AFD) al no determinista (AFND), el cambio estructural más importante radicó en la forma de almacenar y procesar las transiciones en memoria. 

En un AFD convencional, la lectura de un símbolo desde un estado específico conduce invariablemente a un único estado destino. Sin embargo, para cumplir con las características de un AFND, la lógica interna se modificó para soportar *múltiples transiciones para un mismo par estado-símbolo*. 

Esto se logró adaptando las estructuras de datos internas para que un solo origen y símbolo puedan apuntar a un conjunto de estados destino simultáneamente, garantizando además mediante validaciones que el alfabeto ingresado se restrinja estrictamente a los caracteres {0, 1, 2}.

<p align="center">
  <img src="https://github.com/user-attachments/assets/01c556d4-3788-4ee0-b987-7ee862baab8b">
  <br>
  Figura 1
</p>

A nivel de interfaz gráfica, se amplió el módulo de captura de transiciones para dar libertad al usuario de ingresar estos múltiples estados de forma intuitiva, permitiendo capturar varios destinos en una sola regla.

<p align="center">
  <img src="https://github.com/user-attachments/assets/9deedcd1-155d-4f8c-b8e9-479baa657150">
  <br>
  Figura 2
</p>

#### Visualización del Procesamiento

Para ofrecer una representación clara del comportamiento no determinista, la interfaz gráfica se actualizó para mostrar en tiempo real la evolución de la cadena evaluada. 

Durante la simulación paso a paso, el sistema expone:
* *Conjunto de Estados Activos:* Un indicador visual en pantalla que muestra exactamente en qué estados se encuentra el autómata de forma simultánea en un instante dado.
* *Rutas de Ramificación:* El registro de los múltiples caminos tomados durante el procesamiento de la cadena.
* *Resultado Final:* Al concluir la lectura, el sistema emite de forma clara la aceptación o rechazo de la cadena, validando la aceptación únicamente si al menos uno de los estados en el conjunto activo final corresponde a un estado de aceptación definido.

### 2.2 Simulación de Autómatas No Deterministas con Transiciones Lambda (AFN-λ)

Para soportar este formalismo, la aplicación fue extendida no solo en su motor de evaluación, sino también en su interfaz para permitir el ingreso y la visualización de transiciones vacías (Lambda).

#### Soporte y Representación en la Interfaz
Se modificó el módulo de captura de transiciones para aceptar un símbolo especial (representado por λ o un campo en blanco) que denota la transición nula. Gráficamente, el programa diferencia estas transiciones del resto para que el usuario pueda identificar rápidamente los saltos espontáneos entre estados.

<p align="center">
  <img src="https://github.com/user-attachments/assets/9b9acfd9-f698-4b05-9313-e98c604c6219">
  <br>
  Figura 3
</p>

#### Cálculo de la λ-Clausura (Clausura Lambda)
El núcleo de la simulación del AFN-λ es el algoritmo de *λ-clausura. Se implementó una función recursiva (o iterativa mediante pilas/colas) que, dado un estado o conjunto de estados, calcula y devuelve todos los estados que son alcanzables transitando *únicamente a través de transiciones Lambda, incluyéndose a sí mismo.

La aplicación incluye una opción visual que permite al usuario seleccionar cualquier estado y ver en pantalla cuál es su clausura lambda correspondiente.

<p align="center">
  <img src="https://github.com/user-attachments/assets/8daff52d-4545-4f1c-a112-5ecdafa025ad">
  <br>
  Figura 4
</p>

#### Adaptación del Algoritmo de Simulación
El motor de evaluación de cadenas se adaptó para integrar las transiciones lambda siguiendo este flujo en cada paso:
1. *Aplicar λ-clausura inicial:* Antes de leer cualquier símbolo, el conjunto de estados activos se expande con sus respectivas clausuras lambda.
2. *Consumo de símbolo:* Se evalúan las transiciones normales con el carácter actual de la cadena.
3. *Aplicar λ-clausura final:* Al conjunto de estados resultantes se le vuelve a aplicar la clausura lambda para preparar el terreno para la siguiente iteración (o para la evaluación de aceptación final).

### 2.3 Conversión entre Tipos de Autómatas

Para dotar al simulador de versatilidad analítica y cumplir con los requerimientos de la práctica, se implementaron los algoritmos estándar para transformar modelos complejos en sus equivalentes más simples, garantizando rigurosamente que el lenguaje aceptado siga siendo exactamente el mismo.

#### Conversión de AFND a AFD (Algoritmo de Subconjuntos)
Se integró el algoritmo de construcción de subconjuntos. A nivel de lógica e interfaz, el programa es capaz de procesar y mostrar la generación de la nueva tabla de transiciones. En este proceso, cada estado del nuevo AFD creado representa un conjunto de estados del AFND original, y el sistema determina automáticamente cuáles de estos nuevos conjuntos se convierten en estados de aceptación.

<p align="center">
  <img src="https://github.com/user-attachments/assets/e2225176-644e-4754-b634-6da123b60229">
  <br>
  Figura 5
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/5ad99fda-5bf7-4a47-ac30-ee440b0ad1af">
  <br>
  Figura 6
</p>

#### Conversión de AFN-λ a AFND
Se desarrolló el proceso lógico para transformar un autómata con transiciones vacías en un no determinista tradicional. Esto se logra implementando el algoritmo de eliminación de transiciones lambda, el cual emplea las clausuras previamente calculadas para remapear los destinos válidos y omitir los saltos nulos.

<p align="center">
  <img src="https://github.com/user-attachments/assets/5c2be62c-fe95-4ad5-bdf0-7da282155abd">
  <br>
  Figura 7
</p>

### 2.4 Funciones Adicionales (Importación, Exportación y Pruebas Múltiples)

Cabe destacar que las funcionalidades de persistencia de datos (importación y exportación de autómatas desde/hacia archivos .jff compatibles con JFLAP), así como el módulo de validación masiva de cadenas mediante archivos externos, fueron desarrolladas e integradas exitosamente durante la Práctica 2. 

Para esta entrega, dichas funciones se heredaron estructuralmente y se adaptaron para soportar los nuevos modelos no deterministas, manteniendo un ecosistema de pruebas robusto y eficiente sin necesidad de reescribir el motor base.

### 2.6 Minimización de Autómatas Finitos Deterministas

El módulo de optimización permite reducir un autómata determinista a su mínima expresión (menor número de estados posible) sin alterar el lenguaje que acepta. 

Para lograr esto, se implementó el algoritmo de clases de equivalencia (método de llenado de tabla). El proceso identifica y elimina estados inaccesibles, y posteriormente construye una tabla de pares para descubrir qué estados son indistinguibles entre sí para fusionarlos en un único estado representativo.

#### Visualización y Comparación Lado a Lado
Cumpliendo con los requerimientos de análisis visual, la interfaz presenta una vista comparativa donde se puede observar el autómata original frente a su versión ya minimizada. El sistema documenta automáticamente cuántos estados fueron eliminados y cuáles grupos de estados fueron consolidados.

<p align="center">
  <img src="https://github.com/user-attachments/assets/3256f878-92bc-49a1-a07e-358bb9607396">
  <br>
  Figura 8
</p>
<p align="center">
  <img src="https://github.com/user-attachments/assets/ef6cc2b7-a452-4333-acb7-4b44127e6536">
  <br>
  Figura 9
</p>

## Conclusión

El desarrollo de esta práctica consolidó la comprensión algorítmica de la teoría de autómatas. La transición de un modelo AFD a uno no determinista (AFND y AFN-λ) exigió rediseñar el motor de procesamiento para evaluar múltiples caminos simultáneos, destacando el uso de la λ-clausura para manejar transiciones vacías.

Asimismo, la implementación de los algoritmos de subconjuntos y de minimización (clases de equivalencia) demostró de forma práctica cómo los autómatas complejos pueden transformarse en máquinas de estados mínimas y optimizadas sin alterar el lenguaje que aceptan. 

En conjunto, la herramienta resultante no solo cumple con los requerimientos de la unidad de aprendizaje, sino que facilita enormemente el análisis y la comprobación visual de estos conceptos matemáticos abstractos.
