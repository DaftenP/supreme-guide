from transformers import AutoTokenizer
import torch
from lime.lime_text import LimeTextExplainer
import time


class NLPModel:
    def __init__(self, path):
        self.device = torch.device('cuda' if torch.cuda.is_available() else 'cpu')
        self.model = torch.load("./model/" + path, map_location=self.device)
        self.model.to(self.device).eval()
        self.MODEL_NAME = "skplanet/dialog-koelectra-small-discriminator"
        self.tokenizer = AutoTokenizer.from_pretrained(self.MODEL_NAME)
        print('Model initialized')

    def sentence_predict(self, sentence):
        # 입력된 문장 토크나이징
        tokenized_sentence = self.tokenizer(
            sentence,
            return_tensors="pt",
            truncation=True,
            add_special_tokens=True,
            max_length=128
        ).to(self.device)

        # 예측
        with torch.no_grad():
            outputs = self.model(
                input_ids=tokenized_sentence["input_ids"],
                attention_mask=tokenized_sentence["attention_mask"],
                token_type_ids=tokenized_sentence["token_type_ids"]
            )

        # 결과 return
        logits = outputs[0]
        logits = logits.detach().cpu()
        result = logits.argmax(-1)
        return result

    def predict_fn(self, texts):
        inputs = self.tokenizer(texts, return_tensors='pt', truncation=True, padding=True).to(self.device)
        outputs = self.model(**inputs)
        return outputs.logits.detach().cpu().numpy()

    def sentence_purifier(self, text):
        harmful_words = []
        # print(self.model.get_device())
        explainer = LimeTextExplainer(class_names=['Not Offensive', 'Offensive'])
        # flag = True
        while not (self.sentence_predict(text)):
            # LIME을 사용하여 예측을 설명합니다.
            exp = explainer.explain_instance(text, self.predict_fn, num_features=2)
            harmful_word = exp.as_list()[0][0]
            print(f'detected word   : {harmful_word}')
            harmful_words.append(text.split().index(harmful_word))
            # 결과를 출력합니다.
            text = text.replace(harmful_word, '#')
            # print(f'replaced text : {text}')
        return harmful_words


if __name__ == '__main__':
    model = NLPModel('model_10.pt')
    while True:
        print('-' * 30)
        sentence = input("input           : ")
        st = time.time()
        print(model.sentence_predict(sentence))
        et = time.time()
        print(f'time            : {str(et - st)[:6]} sec')
