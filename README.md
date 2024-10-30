# Spring MVC - JSON View API

## Описание проекта
Этот проект представляет собой RESTful API для управления информацией о пользователях и их заказах в интернет-магазине. Используя аннотацию `@JsonView`, API предоставляет различные представления JSON в зависимости от контекста, что позволяет эффективно управлять данными.

## Сущности
### User
Содержит информацию о пользователе, такую как имя, адрес электронной почты, идентификатор и т.д.

### Order
Представляет заказ пользователя и содержит информацию о товарах, сумме заказа и статусе.

## Реализация CRUD операций
Проект реализует CRUD операции для пользователей и их заказов с использованием Spring Data JPA.

## RESTful API
API предоставляет следующие эндпоинты:
- **GET /api/users**: Получение списка всех пользователей (без деталей заказов).
- **GET /api/users/{id}**: Получение информации о конкретном пользователе (включая детали заказов).
- **POST /api/users**: Создание нового пользователя.
- **PUT /api/users/{id}**: Обновление информации о пользователе.
- **DELETE /api/users/{id}**: Удаление пользователя.

## Использование @JsonView
Для определения различных представлений JSON в зависимости от контекста созданы интерфейсы представлений:
- `Views.UserSummary`: Отображает только базовую информацию о пользователе.
- `Views.UserDetails`: Включает детали заказов пользователя.

## Обработка ошибок и валидация
Проект обеспечивает корректную обработку ошибок и возвращение соответствующих HTTP-статусов. Валидация входных данных включает проверку корректности электронной почты.

## Тестирование
Модульные тесты написаны для проверки различных представлений JSON при использовании `@JsonView`, обеспечивая надежность API.

## Пример кода
Ниже приведен пример реализации контроллера пользователя:

```java
@RestController
@RequestMapping("/api/users")
public class ShopUserController {

    private final ShopUserInterface userService;

    @Autowired
    public ShopUserController(ShopUserInterface userService) {
        this.userService = userService;
    }

    @GetMapping
    @JsonView(ShopUser.SummaryView.class)
    public List<ShopUser> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @JsonView(ShopUser.DetailView.class)
    public ShopUser getUserById(@PathVariable Long id) {
        return userService.getUserById(id).orElseThrow(() -> new UserNotFoundException("Пользователь с id " + id + " не найден"));
    }

    @PostMapping
    public ResponseEntity<ShopUser> createUser(@RequestBody ShopUser user){
        ShopUser createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShopUser> updateUser(@PathVariable Long id, @RequestBody ShopUser userDetails){
        ShopUser updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
```

## Заключение
Данный проект демонстрирует использование Spring MVC и JSON View для построения гибкого и удобного RESTful API, обеспечивая управление данными о пользователях и их заказах в интернет-магазине.