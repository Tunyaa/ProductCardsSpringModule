🛒 Product Cards Spring Module
Spring Boot приложение для управления товарами, заказами и позициями с современным веб-интерфейсом.

✨ Особенности
Категория	Возможности
🔐 Безопасность	Spring Security, ролевая модель, CSRF защита
📊 Мониторинг	AOP логирование, тайминг методов, обработка ошибок
📱 Интерфейс	Адаптивный дизайн, мобильная оптимизация
🗃️ Данные	PostgreSQL, Spring Data JPA, миграции
🚀 Технологический стек
https://img.shields.io/badge/Java-17%252B-orange?style=for-the-badge&logo=openjdk
https://img.shields.io/badge/Spring_Boot-3.1-green?style=for-the-badge&logo=springboot
https://img.shields.io/badge/PostgreSQL-15-blue?style=for-the-badge&logo=postgresql
https://img.shields.io/badge/Thymeleaf-3.1-green?style=for-the-badge&logo=thymeleaf

Backend: Spring Boot 3 • Spring Security • Spring Data JPA • AOP • Lombok

Frontend: Thymeleaf • HTML5 • CSS3 • JavaScript

Database: PostgreSQL • H2 (для разработки)

Tools: Maven • Git • IntelliJ IDEA

📦 Быстрый старт
bash
# 1. Клонировать репозиторий
git clone https://github.com/yourusername/ProductCardsSpringModule.git

# 2. Перейти в директорию проекта
cd ProductCardsSpringModule

# 3. Запустить приложение
mvn spring-boot:run
📍 Приложение доступно по адресу: http://localhost:8080

👥 Аккаунты для входа
Роль	Логин	Пароль	Доступ
👑 Администратор	admin	admin123	Полный доступ
👨‍💼 Менеджер	manager	manager123	Управление заказами
👤 Пользователь	user	user123	Базовые операции
🏗️ Архитектура проекта
plaintext
src/
├── main/
│   ├── java/
│   │   └── module/ProductCardsSpringModule/
│   │       ├── config/          # 🛠️ Конфигурации
│   │       ├── controller/      # 🎮 MVC контроллеры
│   │       ├── service/         # ⚡ Бизнес-логика
│   │       ├── repository/      # 🗃️ Data JPA
│   │       ├── model/          # 📊 Сущности БД
│   │       ├── aspect/         # 📝 AOP аспекты
│   │       └── dto/            # 📨 Data Transfer Objects
│   └── resources/
│       ├── static/             # 🎨 CSS, JS, изображения
│       └── templates/          # 📝 Thymeleaf шаблоны
📡 API Endpoints
Метод	Endpoint	Описание
GET	/	Главная страница с аутентификацией
GET	/dashboard	Панель управления
GET	/orders	📋 Управление заказами
GET	/products	🛍️ Просмотр товаров
POST	/login	🔐 Аутентификация
POST	/logout	🚪 Выход из системы
🎯 Ключевые возможности
🛍️ Управление товарами
✅ Просмотр карточек товаров с изображениями

✅ Поиск и фильтрация по категориям

✅ Сортировка и пагинация

📋 Работа с заказами
java
// Пример создания заказа
Order order = new Order();
order.setPositions(positions);
orderService.createOrder(order);
🔐 Система безопасности
✅ Ролевая модель доступа (ADMIN, MANAGER, USER)

✅ Форма аутентификации

✅ Защита от CSRF атак

📊 Мониторинг и логирование
java
@Around("execution(* com.example.service.*.*(..))")
public Object monitorMethod(ProceedingJoinPoint joinPoint) throws Throwable {
    // AOP логирование
    long start = System.currentTimeMillis();
    Object result = joinPoint.proceed();
    long duration = System.currentTimeMillis() - start;
    logger.info("Метод {} выполнен за {} мс", 
                joinPoint.getSignature(), duration);
    return result;
}
🎨 Скриншоты
Главная страница	Панель управления
https://via.placeholder.com/400x250?text=Login+Page	https://via.placeholder.com/400x250?text=Dashboard
Управление заказами	Мобильная версия
https://via.placeholder.com/400x250?text=Orders	https://via.placeholder.com/400x250?text=Mobile+View
🚦 Статус разработки
✅ Завершено:

Базовая архитектура приложения

Система аутентификации и авторизации

CRUD операции для товаров и заказами

AOP мониторинг и логирование

Адаптивный пользовательский интерфейс

🔄 В разработке:

Расширенная система отчетности

Интеграция с платежными системами

Push-уведомления

Экспорт данных в Excel

📋 Планируется:

Микросервисная архитектура

Docker контейнеризация

Kubernetes развертывание

CI/CD пайплайн

🤝 Как contribute
🍴 Форкните репозиторий

🌿 Создайте feature ветку: git checkout -b feature/amazing-feature

💾 Закоммитьте изменения: git commit -m 'Add amazing feature'

📤 Запушьте ветку: git push origin feature/amazing-feature

🔃 Создайте Pull Request

📊 Статистика проекта
https://img.shields.io/badge/Java-100%2525-orange?style=flat-square
https://img.shields.io/badge/Spring-85%2525-green?style=flat-square
https://img.shields.io/badge/HTML/CSS-15%2525-blue?style=flat-square

https://img.shields.io/badge/Lines_of_Code-5K-success?style=flat-square
https://img.shields.io/badge/Last_Commit-Today-brightgreen?style=flat-square
https://img.shields.io/badge/License-MIT-blue?style=flat-square

