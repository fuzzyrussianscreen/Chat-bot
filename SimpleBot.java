import java.util.*;
import java.util.regex.*;



public class SimpleBot {
	
	  final String[] COMMON_PHRASES = {
			"Задайте вопрос корректнее. Вы можете обратиться в раздел 
			'Вопросы и ответы'(http://pk.ulstu.ru/?nav=faq).
			 Так же вы можете обратиться по телефону 8(8422)43-05-05",
					   
			"Нет ничего ценнее слов, сказанных к месту и ко времени.",
		        "Порой молчание может сказать больше, нежели уйма слов.",
		        "Перед тем как писать/говорить всегда лучше подумать.",
		        "Вежливая и грамотная речь говорит о величии души.",
		        "Приятно когда текст без орфографических ошибок.",
		        "Многословие есть признак неупорядоченного ума.",
		        "Слова могут ранить, но могут и исцелять.",
		        "Записывая слова, мы удваиваем их силу.",
		        "Кто ясно мыслит, тот ясно излагает.",
		        "Боюсь Вы что-то не договариваете."};
		    final String[] EXCLUSIVE_ANSWERS = {
			"К сожалению, я не знаю ответа на данный вопрос. 
			Вы можете обратиться в раздел 'Вопросы и ответы'(http://pk.ulstu.ru/?nav=faq).
			Так же вы можете обратиться по телефону 8(8422)43-05-05",
			    
		        "Вопрос непростой, прошу тайм-аут на раздумья.",
		        "Не уверен, что располагаю такой информацией.",
		        "Может лучше поговорим о чём-то другом?",
		        "Простите, но это очень личный вопрос.",
		        "Не уверен, что Вам понравится ответ.",
		        "Поверьте, я сам хотел бы это знать.",
		        "Вы действительно хотите это знать?",
		        "Уверен, Вы уже догадались сами.",
		        "Зачем Вам такая информация?",
		        "Давайте сохраним интригу?"};
		    final Map<String, String> PATTERNS_FOR_ANALYSIS = new HashMap<String, String>() {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
		        // hello
		        put("хай", "hello");
		        put("привет", "hello");
		        put("здорово", "hello");
		        put("здравствуй", "hello");
		        put("здравствуйте, hello", null);
		        // who
		        put("кто\\s.*ты", "who");
		        put("ты\\s.*кто", "who");
		        // name
		        put("как\\s.*зовут", "name");
		        put("как\\s.*имя", "name");
		        put("есть\\s.*имя", "name");
		        put("какое\\s.*имя", "name");
		        // howareyou
		        put("как\\s.*дела", "howareyou");
		        put("как\\s.*жизнь", "howareyou");
		        // whatdoyoudoing
		        put("зачем\\s.*тут", "whatdoyoudoing");
		        put("зачем\\s.*здесь", "whatdoyoudoing");
		        put("что\\s.*делаешь", "whatdoyoudoing");
			put("что\\s.*можешь", "whatdoyoudoing");
			put("что\\s.*умеешь", "whatdoyoudoing");
		        put("чем\\s.*занимаешься", "whatdoyoudoing");
		        // whatdoyoulike
		        put("что\\s.*нравится", "whatdoyoulike");
		        put("что\\s.*любишь", "whatdoyoulike");
		        // iamfeelling
		        put("кажется", "iamfeelling");
		        put("чувствую", "iamfeelling");
		        put("испытываю", "iamfeelling");
		        // yes
		        put("^да", "yes");
		        put("согласен", "yes");
		        // whattime
		        put("который\\s.*час", "whattime");
		        put("сколько\\s.*время", "whattime");
		        // bye
		        put("прощай", "bye");
		        put("увидимся", "bye");
		        put("до\\s.*свидания", "bye");
				
			put("какие\\s.*баллы", "Проходной балл");
			put("Сколько стоит обучение", "Стоимость обучения");
			put("Сколько баллов за аттестат", "Дополнительные баллы");
			put("Как поступить", "Информация о поступлении");
			put("Целевой приём", "Целевой приём");
			put("Сайт УЛГТУ", "Сайт УЛГТУ");
			put("Какие факультеты", "Факультеты");
			put("Варианты испытаний", "Вступительные испытания демовар");
			put("Схема корпусов", "Корпуса УЛГТУ");
			put("Сайт\\s.*приёмной\\s.*комиссии", "Главная приёмной комиссии");
			put("Места для поступления", "Количество мест");
			put("Что\\s.*сдавать", "Вступительные испытания");
			put("группа", "Группа ВК");
			put("кафедры", "Кафедры");
			put("зачисленные", "Приказы");
			put("Как в университете со спортом", "Спорт");
		    }};
		    final Map<String, String> ANSWERS_BY_PATTERNS = new HashMap<String, String>() {/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			{
		        put("hello", "Здравствуйте, рад Вас видеть.");
		        put("who", "Я обычный чат-бот.");
		        put("name", "Зовите меня Чаттер :)");
		        put("howareyou", "Спасибо, что интересуетесь. У меня всё хорошо.");
		        put("whatdoyoudoing", "Я могу помочь вам ознакомиться с актуальной информацией о ходе приемной комиссии");
			put("whatdoyoudoing", "Я упрощаю работу с сайтом. Примеры команд:'Проходной балл', 'Факультеты' 'Сайт УЛГТУ'");
		        put("whatdoyoulike", "Мне нравиться думать что я не просто программа.");
		        put("iamfeelling", "Как давно это началось? Расскажите чуть подробнее.");
		        put("yes", "Согласие есть продукт при полном непротивлении сторон.");
		        put("bye", "До свидания. Надеюсь, ещё увидимся.");
				
				
				put("Проходной балл", "Информация о проходных баллах доступна по ссылке http://pk.ulstu.ru/Documents/2017/PB1416.pdf");
				put("Стоимость обучения", "Приказы о стоимости опубликованы на сайте ПФУ http://www.ulstu.ru/main/view/article/18107");
				put("Дополнительные баллы", "за аттестат с отличием дополнительно начисляется 4 балла");
				put("Информация о поступлении", "Информацию о поступлении можно узнать по ссылке http://pk.ulstu.ru/index.php?nav=rules2018");
				put("Целевой приём", "Информацию о целевом приёме можно узнать по ссылке http://pk.ulstu.ru/index.php?nav=celpr");
				put("Сайт УЛГТУ", "Сайт УЛГТУ находится по ссылке http://www.ulstu.ru/");
				put("Факультеты", "Список факультетов можно найти по ссылке http://www.ulstu.ru/main/view/article/4010");
				put("Вступительные испытания демовар", "Демоварианты вступительных испытаний можно найти по ссылке http://pk.ulstu.ru/index.php?id=1754");
				put("Корпуса УЛГТУ", "Схему расположений корпусов УЛГТУ можно найти по ссылке http://pk.ulstu.ru/index.php?id=81");
				put("Главная приёмной комиссии", "Сайт приёмной комиссии http://pk.ulstu.ru/index.php?id=81");
				put("Количество мест", "Количество мест можно узнать по ссылке http://pk.ulstu.ru/Documents/2018/KCP_2018.pdf");
				put("Вступительные испытания", "Необходимые предметы можно увидеть по ссылке http://pk.ulstu.ru/Documents/2018/per_vs_isp.pdf");
				put("Группа ВК", "Официальная группа ВКонтакте https://vk.com/univer.ulstu");
				put("Кафедры", "Список кафедр можно найти по ссылке http://www.ulstu.ru/main/view/article/4020");
				put("Приказы", "Приказы к зачислению можно найти по ссылке http://pk.ulstu.ru/?nav=orders");
				put("Спорт", "Информация о спортивной жизни университета доступна здесь http://pk.ulstu.ru/index.php?nav=sport");
		    }};
	
	Pattern pattern;
	Random random;
	Date date;
	
	
	SimpleBot(){
		random = new Random();
		date = new Date();
	}

	  public String sayInReturn(String msg, boolean ai) {
	        String say = (msg.trim().endsWith("?"))?
	            EXCLUSIVE_ANSWERS[random.nextInt(EXCLUSIVE_ANSWERS.length)]:
	            COMMON_PHRASES[random.nextInt(COMMON_PHRASES.length)];
	        if (ai) {
	            String message =
	                String.join(" ", msg.toLowerCase().split("[ {,|.}?]+"));
	            for (Map.Entry<String, String> o : PATTERNS_FOR_ANALYSIS.entrySet()) {
	                pattern = Pattern.compile(o.getKey());
	                if (pattern.matcher(message).find())
	                    if (o.getValue().equals("whattime")) return date.toString();
	                    else return ANSWERS_BY_PATTERNS.get(o.getValue());
	            }
	        }
	        return say;
	  }
}
	  
	
	
