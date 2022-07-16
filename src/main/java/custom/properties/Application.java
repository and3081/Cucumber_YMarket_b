package custom.properties;

import org.aeonbits.owner.Config;

/**
 * Интерфейс для работы с проперти из файла application.properties и системными проперти
 * usage:  TestData.application.имяМетодаКлюча()
 * Описание в файле проперти
 */
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "file:application.properties"
})
public interface Application extends Config {
    @Key("base.url.yandex")
    String baseUrlYandex();
}
