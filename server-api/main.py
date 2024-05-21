from typing import Optional

import uvicorn
from fastapi import FastAPI
from pydantic import BaseModel
from model import NLPModel

app = FastAPI()
model = NLPModel('model_10.pt')


# Pydantic을 사용하여 요청 데이터의 구조를 정의합니다.
class Article(BaseModel):
    id: Optional[int] = None
    articleId: int
    content: str
    userId: Optional[str] = None
    userName: Optional[str] = None
    create_date: Optional[str] = None
    harmful: Optional[bool] = None


@app.post("/check")
async def check(article: Article):
    content_sentence = list(article.content.split("\n"))
    for sentence in content_sentence:
        if not model.sentence_predict(sentence):
            return {'msg': f'부적절한 내용 입니다. ({sentence})'}
    return None


if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)