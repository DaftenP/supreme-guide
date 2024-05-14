from typing import Optional
from fastapi import FastAPI
from pydantic import BaseModel
from model import NLPModel

app = FastAPI()
model = NLPModel('model_10.pt')


# Pydantic을 사용하여 요청 데이터의 구조를 정의합니다.
class Article(BaseModel):
    board_id: int
    board_title: Optional[str] = None
    board_content: Optional[str] = None
    board_view: int
    board_writer: Optional[str] = None
    board_created_time: Optional[str] = None


@app.post("/check")
async def check(article: Article):
    if not model.sentence_predict(article.board_title):
        return {'msg': f'부적절한 제목 입니다. ({article.board_title})'}
    content_sentence = list(article.board_content.split("\n"))
    for sentence in content_sentence:
        if not model.sentence_predict(sentence):
            return {'msg': f'부적절한 내용 입니다. ({sentence})'}
    return {"msg": ""}
