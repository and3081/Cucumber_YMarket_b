#language: ru
@AllTests
Функция: Проверка выборки на Яндекс Маркет
  Предыстория:
    Когда Открываем Яндекс
    Тогда Проверяем title страницы Яндекс
    Затем Переходим на Яндекс Маркет
    Тогда Проверяем title страницы Яндекс Маркет

  #noinspection NonAsciiCharacters
  Сценарий: Проверка выборки товара по производителю на Яндекс Маркет
    Затем Открываем каталог и раздел
      | Электроника |
    Тогда Проверяем заголовок раздела
      | Электроника |
    Затем Открываем подраздел
      | Смартфоны   |
    Тогда Проверяем заголовок подраздела в крошках
      | Смартфоны   |
    Затем Раскрываем перечень производителей, ищем и отмечаем нужного
      | Apple        |
    И Выбираем количество просмотра
      | 48          |
    Тогда Проверяем производителя на всех найденных страницах
      | Apple        |

#  //        return Stream.of(arguments(listChapters, "Apple", countForOld));
#  //                         arguments(listChapters, "Google", countForOld),
#  //                         arguments(listChapters, "HONOR", countForOld),
#  //                         arguments(listChapters, "HUAWEI", countForOld),
#  //                         arguments(listChapters, "Nokia", countForOld),
#  //                         arguments(listChapters, "OnePlus", countForOld),
#  //                         arguments(listChapters, "OPPO", countForOld),
#  //                         arguments(listChapters, "realme", countForOld),
#  //                         arguments(listChapters, "Samsung", countForOld),
#  //                         arguments(listChapters, "vivo", countForOld),
#  //                         arguments(listChapters, "Xiaomi", countForOld),
#  //                         arguments(listChapters, "ZTE", countForOld));