import java.util.*;
import java.util.regex.*;



public class SimpleBot {

	final String[] COMMON_PHRASES = {
			"Задайте вопрос корректнее. Вы можете обратиться в раздел 'Вопросы и ответы'(http://pk.ulstu.ru/?nav=faq). " +
					"Так же вы можете обратиться по телефону 8(8422)43-05-05"};
	final String[] EXCLUSIVE_ANSWERS = {
			"К сожалению, я не знаю ответа на данный вопрос. " +
					"Вы можете обратиться в раздел 'Вопросы и ответы'(http://pk.ulstu.ru/?nav=faq)." +
					" Так же вы можете обратиться по телефону 8(8422)43-05-05"};
	final Map<String, String> PATTERNS_FOR_ANALYSIS = new HashMap<String, String>() {/**
	 *
	 */
	private static final long serialVersionUID = 1L;

		{
			// hello
			put("хай", "hello");
			put("пр[ие]вет", "hello");
			put("зд[оа]рово", "hello");
			put("здравствуй", "hello");
			put("здравствуйте", "hello");
			// hello|e
			put("hi", "hello|e");
			put("hello", "hello|e");
			put("hey", "hello|e");
			put("whats'up", "hello|e");
			// good morning/ day/evening/night
			put("доброе утро", "good morning");
			put("добрый день", "good day");
			put("добрый вечер", "good evening");
			put("доброй ночи", "good night");
			// who
			put("кто\\s.*ты", "who");
			put("[вты]\\s.*кто", "who");
			// who|e
			put("who\\s.*you", "who|e");
			// name
			put("как\\s.*зовут", "name");
			put("как\\s.*имя", "name");
			put("есть\\s.*имя", "name");
			put("какое\\s.*имя", "name");
			// name|e
			put("what\\s.*name", "name|e");
			// howareyou
			put("как\\s.*дела", "howareyou");
			put("как\\s.*жизнь", "howareyou");
			// howareyou|e
			put("how\\s.*you", "howareyou|e");
			put("are\\s.*ok", "howareyou|e");
			// whatdoyoudoing
			put("зачем\\s.*тут", "whatdoyoudoing");
			put("зачем\\s.*здесь", "whatdoyoudoing");
			put("что\\s.*делаешь", "whatdoyoudoing");
			put("что\\s.*можешь|умеешь*", "чтотыделаешь");
			put("чем\\s.*занимаешься", "whatdoyoudoing");
			// whatdoyoudoing|e
			put("what\\s.*doing", "whatdoyoudoing|e");
			put("why\\s.*you", "whatdoyoudoing|e");
			//саня пидор

			// whatdoyoulike|e
			put("what\\s.*like", "whatdoyoulike|e");
			put("what\\s.*fond", "whatdoyoulike|e");
			put("what\\s.*prefer", "whatdoyoulike|e");
			put("what\\s.*favourite", "whatdoyoulike|e");
			// iamfeelling
			put("кажется", "iamfeelling");
			put("чувствую", "iamfeelling");
			put("испытываю", "iamfeelling");
			// iamfeelling|e
			put("feel", "iamfeelling|e");
			put("sure", "iamfeelling|e");
			put("hope", "iamfeelling|e");
			// yes
			put("^да", "yes");
			put("согласен", "yes");
			// yes|e
			put("^yes", "yes|e");
			put("yeah", "yes|e");
			put("right", "yes|e");
			put("ok", "yes|e");
			// whattime
			put("который\\s.*час", "whattime");
			put("сколько\\s.*время", "whattime");
			// whattime|e
			put("what\\s.*time", "whattime|e");
			//intro|e
			put("can\\s.*help", "intro|e");
			put("can\\s.*give","giving|e");

			//gotosite
			put("приемн(ой|ая) ко(м|мм)и(с|сс)и(я|и|й)*", "gotosite");
			//gotosite|e
			put("can\\s.*open website", "gotosite|e");
			put("балл*", "Проходной балл");

			put("what\\s.*the passing scores", "the passing scores");
			put("стоит\\s.|нужно заплатить\\s.*обучение*","Стоимость обучения");
			put("обучение*","Процесс обучения");
			put("what\\s.*price for education", "educationprice");
			put("how much\\s.* education", "educationprice");
			put("индивидуальные достижения*|дополнительные баллы*", "Дополнительные баллы");
			put("scores\\s.*for\\s.*individual achievements", "addscores");
			put("how much scores\\s.*for\\s.*certificate", "addscores");
			put("how much scores\\s.*for\\s.*diploma","addscores");

			put("поступить*", "Информация о поступлении");
			put("how\\s.*apply", "applying");
			put("целевой при[её]м*", "Целевой приём");
			put("сайт [УуЛлГгТтУу]*", "Сайт УЛГТУ");
			put("спорт*", "спорт");
			put("ф[ао]культет*", "факультеты");
			put("what сourse directory", "directory");
			put("корпус*", "Корпуса УЛГТУ");
			put("the scheme of campus", "campus");

			put("мест*", "Количество мест");
			put("seats for admission", "seats for admission");

			put("сдавать|вступительные испытания*", "Вступительные испытания");
			put("what\\s.*exams", "exams");
			put("группа*", "Группа ВК");
			put("vk*", "vk");
			put("кафедры*", "Кафедры");
			put("departments", "departments");
			put("зачисленные*", "Приказы");
			put("приказы*", "Приказы");
			put("enrolled", "enrolled");

			// bye
			put("прощай*", "bye");
			put("увидимся*", "bye");
			put("до\\s.*свидания", "bye");
			// bye|e thank
			put("bye", "bye|e");
			put("thank\\s.*", "thank");
			//respect
			put("cool", "cool");
			put("amazing", "amazing");
			put("awesome", "awesome");
			put("real talk", "real");

			put("помоги*", "помощь");

		}};
	final Map<String, String> ANSWERS_BY_PATTERNS = new HashMap<String, String>() {/**
	 *
	 */
	private static final long serialVersionUID = 1L;

		{
			put("hello", "Здравствуйте, рад Вас видеть.");
			put("hello|e", "Hello my friend. Glad to hear from you!");

			put("good morning", "Утро доброе!");
			put("good day", "Добрый день!");
			put("good evening", "Добрый вечер!");
			put("good night", "Доброй ночи!");
			put("who", "Я чат-бот Политехник");
			put("who|e", "I'm Chat-bot");
			put("name", "Зовите меня Политехник :)");
			put("name|e", "I'm Politechnik :)");
			put("howareyou", "Спасибо, что интересуетесь. У меня всё хорошо.");
			put("howareyou|e", "Thank you for your attention. I'm good, and you?");
			put("whatdoyoudoing", "Я могу помочь вам ознакомиться с актуальной информацией о ходе приемной комиссии");
			put("whatdoyoudoing|e", "I can find anythink you like!");
			put("чтотыделаешь", "Я упрощаю работу с сайтом и экономлю ваше время)");
			put("whatdoyoulike|e", "I really fond of helping people and i dream of making the world brighter and life easier");
			put("iamfeelling", "Как давно это началось? Расскажите чуть подробнее.");
			put("gotosite","http://pk.ulstu.ru");
			put("gotosite|e","http://pk.ulstu.ru");
			put("yes", "Согласие есть продукт при полном непротивлении сторон.");
			put("yes|e", "Ok my friend");
			put("yes|e", "It's really cool");
			put("bye", "До свидания. Надеюсь, ещё увидимся.");
			put("bye|e", "bye");
			put("bye|e", "Nice to meet you");
			put("bye|e", "See you later");
			put("thank", "glad to help");
			put("помощь", "С радостью, что прикажете делать?");
			put("Проходной балл", "Информация о проходных баллах доступна по ссылке http://pk.ulstu.ru/Documents/2017/PB1416.pdf");
			put("the passing scores", "You can find all information here http://pk.ulstu.ru/Documents/2017/PB1416.pdf");
			put("Стоимость обучения", "Приказы о стоимости опубликованы на сайте ПФУ http://www.ulstu.ru/main/view/article/18107");
			put("educationprice", "Everythink about the price http://www.ulstu.ru/main/view/article/18107");
			put("Процесс обучения", "Вся информация о обучение в Улгту представлена здесь: http://www.ulstu.ru/");
			put("Дополнительные баллы", "Информация о учете индивидуальных достижений абитуриентов ");
			put("addscores", "So, for a certificate with honors awarded an additional 4 points ");
			put("спорт", "О да, в Улгту любят спорт, информация о спортивной жизни университета доступна здесь http://pk.ulstu.ru/index.php?nav=sport");
			put("Информация о поступлении", "Информацию о поступлении можно узнать по ссылке http://pk.ulstu.ru/index.php?nav=rules2018");
			put("applying", "So,let's find out what to do .Tap here  http://pk.ulstu.ru/index.php?nav=rules2018");
			put("Целевой приём", "Информацию о целевом приёме можно узнать по ссылке http://pk.ulstu.ru/index.php?nav=celpr");
			put("Сайт УЛГТУ", "Сайт УЛГТУ находится по ссылке http://www.ulstu.ru/");
			put("факультеты","Список факультетов можно найти по ссылке http://www.ulstu.ru/main/view/article/4010");
			put("directory", "The list of directories right now! Tap  http://www.ulstu.ru/main/view/article/4010");
			put("Вступительные испытания демовар", "Демоварианты вступительных испытаний можно найти по ссылке http://pk.ulstu.ru/index.php?id=1754");
			put("Корпуса УЛГТУ", "Схему расположений корпусов УЛГТУ можно найти по ссылке http://pk.ulstu.ru/index.php?id=81");
			put("campus", "The sheme of campus for you.Tap http://pk.ulstu.ru/index.php?id=81");
			put("Количество мест", "Количество мест можно узнать по ссылке http://pk.ulstu.ru/Documents/2018/KCP_2018.pdf");
			put("seats for admission", "Vacant seats presented here http://pk.ulstu.ru/Documents/2018/KCP_2018.pdf");
			put("Вступительные испытания", "Необходимые предметы можно увидеть по ссылке http://pk.ulstu.ru/Documents/2018/per_vs_isp.pdf");
			put("exams", "You have to pass such exams http://pk.ulstu.ru/Documents/2018/per_vs_isp.pdf");
			put("Группа ВК", "Официальная группа ВКонтакте https://vk.com/univer.ulstu");
			put("vk", "Oficial vk group is here https://vk.com/univer.ulstu");
			put("Кафедры", "Список кафедр можно найти по ссылке http://www.ulstu.ru/main/view/article/4020");
			put("departments", "You can find all information about departments right here http://www.ulstu.ru/main/view/article/4020");
			put("Приказы", "Приказы к зачислению можно найти по ссылке http://pk.ulstu.ru/?nav=orders"	);
			put("enrolled", "All orders to enrollment right here //http://pk.ulstu.ru/?nav=orders"	);
			put("intro|e","I can show you useful information and explain special details");
			put("giving|e","Of course, my friend! I can learn you");
			put("cool", "Yeah, trust me.");
			put("amazing", "Incredible");
			put("awesome", "That's true");
			put("real", "Think about it*)");
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
