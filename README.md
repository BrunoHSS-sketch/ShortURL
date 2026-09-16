# 🔗 Encurtador de URLs (URL Shortener API)

Um serviço de encurtamento de URLs de alta performance desenvolvido em **Java 17** e **Spring Boot**, inspirado na arquitetura de sistemas como o Bit.ly.

Este projeto foi construído com foco em **escalabilidade, baixa latência e boas práticas de Engenharia de Software**, utilizando **Redis** como banco de dados em memória e estrutura totalmente containerizada com **Docker**.

## 🚀 Tecnologias Utilizadas

* **Java 11**
* **Spring Boot 2.7.0** (Web)
* **Redis** (via Jedis) para armazenamento e contagem atômica rápida.
* **Docker & Docker Compose** (Multi-stage build)
* **JUnit 5 & Mockito** (Testes Unitários)
* **Springdoc OpenAPI (Swagger)** (Documentação interativa)
* **Maven**

## 🧠 Decisões de Arquitetura e Engenharia

Este projeto foge do tradicional CRUD e foca em resolver um problema real de sistemas distribuídos:
* **Algoritmo Base62:** A conversão do link curto é feita através de matemática de Base62 (a-z, A-Z, 0-9).
* **Auto-Incremento Atômico:** Para evitar colisões em requisições simultâneas, a geração do ID único é delegada ao comando `INCR` do Redis, garantindo que o Java não crie gargalos de concorrência.
* **Performance de Memória:** O motor matemático utiliza `StringBuilder` para a conversão de base, evitando a recriação desnecessária de `Strings` na memória (protegendo o Garbage Collector e prevenindo *Out Of Memory*).
* **Arquitetura em Camadas:** Código rigorosamente dividido em `Controller`, `Service`, `Repository` e `DTO`, mantendo as regras de negócio 100% isoladas do framework.
* **Tratamento Global de Erros:** Implementação de `@ControllerAdvice` para capturar exceções de negócio e devolver respostas HTTP padronizadas (ex: `400 Bad Request` para URLs inválidas).

## 🐳 Como rodar a aplicação

A aplicação e o banco de dados estão totalmente containerizados. Para rodar, você só precisa do **Docker** e do **Docker Compose** instalados na sua máquina.

1. Clone o repositório:
   ```bash
   git clone [https://github.com/BrunoHSS-sketch/ShortURL.git](https://github.com/BrunoHSS-sketch/ShortURL.git)
   cd ShortURL

2. Execute o comando para subir os containers:
   ```bash
    docker-compose up -d --build

3. O servidor estará rodando em:
    ```bash
   http://localhost:8080/swagger-ui.html
   ```

## 📚 Documentação da API (Swagger)

Com a aplicação rodando, acesse a interface interativa do Swagger para testar as rotas diretamente pelo navegador:

👉 http://localhost:8080/swagger-ui.html

Endpoints Principais
1. Encurtar URL

   Rota: POST /api/encurtar

   Body (JSON):
   JSON

   {
   "originalUrl": "https://www.linkedin.com/in/bruno-henrique-schmitt-dos-santos/"
   }

   Response (200 OK): b

2. Redirecionar

   Rota: GET /api/{codigo}

   Ação: Busca a URL original no Redis. Se existir, retorna um HTTP 302 (Found) redirecionando o usuário para o site de destino. Se não existir, retorna HTTP 404 (Not Found).

## 🧪 Testes Automatizados

A camada de serviço (UrlService) possui cobertura de testes unitários para garantir o correto funcionamento da conversão Base62 e a blindagem contra regras de negócio inválidas.
O banco de dados foi "mockado" utilizando Mockito para garantir testes rápidos e isolados da infraestrutura.

Para rodar os testes localmente via Maven:
```bash
mvn test
```
