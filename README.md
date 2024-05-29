# [LIfe - TIme : LIME 서비스 링크](https://www.uju-lime.site)
<img src="http://img.shields.io/badge/Swagger-85EA2D?style=flat&logo=Swagger&logoColor=white&link=http://api.uju-lime.shop/swagger-ui/index.html#/)]
(http://api.uju-lime.shop/swagger-ui/index.html#/)" height="25">

## 1. 🍋 프로젝트 소개

### 프로젝트 주제
가지고 싶은 취미 상품을 편리하게 관리하고, 구매 결정에 관해 피드백을 받는 플랫폼 서비스

### 주제 선정 배경
일반 상품들과 달리 취미 상품은 구매에 있어 경험이나 전문성이 필요합니다.<br>
처음 취미를 갖거나, 취미 상품에 대한 경험이 부족한 사람들이 있는 반면, 취미에 대한 깊은 이해를 가지고 계시지만 그 정보를 공유할 수 있는 공간이 부족하다고 생각했습니다. 
그래서 취미 상품에 대한 지식을 얻고 구매 결정 대한 피드백을 얻을 수 있는 커뮤니티형 서비스를 기획했습니다.

### 프로젝트를 새로 만드는 이유
1. 기획 아이디어가 좋아서 실제 서비스 운영을 해보고 싶음
2. 기존 경험을 살려 새로운 팀원들과 협업 경험을 쌓아 나가고 싶음
3. 리팩토링 및 리엔지니어링으로 프로젝트 보완(기능이 부족해서 기능을 보완하고자 함) 및 최적화

## 2. 🌸 기능 소개 
### 1. 아이템 관리
여러 사이트에서 주소만 입력하면 아이템 정보를 저장합니다.<br>
이 상품들로 내가 가지고 싶은 아이*템에 대한 리뷰, 투표를 남길 수 있습니다.

### 2. 찜
내가 관심있어 하는 아이템들을 나의 찜목록에서 기호에 맞게 폴더로 관리할 수 있습니다.
갖고 싶은 상품을 기호에 맞게 정리할 수 있어 관리가 편합니다.

### 3. 투표
2가지 상품중 어떤 상품을 구매해야할 지 고민이 될 때 사랆들의 선호도를 파악해 구매를 결정할 수 있습니다.
투표 인원을 설정할 수 있으며 투표는 24시간 동안 진행됩니다.

### 4. 버킷
내가 가지고 싶은 아이템을 버킷에 담아 관리합니다. 실질적인 구매를 계획하는 공간입니다.

### 5. 피드
버킷에서 저장해둔 구매 계획을 사람들에게 공개해 피드백을 받을 수 있는 공간입니다. <br>
사람들은 댓글로 피드백을 남기고, 그중 가장 도움이 된 내용은 채택됩니다.

### 6. 인벤토리
내가 실제로 구매하고 소유한 아이템을 소개합니다. <br>
유저들은 이를 통해서 그 사람의 취미에 대한 전문성을 파악할 수 있습니다.
  
## 3. 🧑🏻‍💻 멤버 소개

| <img src="https://avatars.githubusercontent.com/u/29273437?v=4"> | <img src ="https://avatars.githubusercontent.com/u/98391539?v=4"> | <img src ="https://avatars.githubusercontent.com/u/77893164?v=4"> | <img src ="https://avatars.githubusercontent.com/u/58348662?v=4"> | <img src ="https://avatars.githubusercontent.com/u/114549939?v=4"> | <img src ="https://github.com/HandmadeCloud/HandmadeCloud/assets/77893164/5415f199-8926-44b7-9e13-598a33f3b3a9"> | 
|:----------------------------------------------------------------:|:-----------------------------------------------------------------:|:-----------------------------------------------------------------:|:-----------------------------------------------------------------:|:------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------------------------:|
|        [BE 강병곤](https://github.com/Curry4182)                    |               [BE 박이슬](https://github.com/Yiseull)                |          [BE 조재현 (팀장)](https://github.com/HandmadeCloud)          |               [FE 진예림](https://github.com/yeeeerim)               |              [FE 박하민](https://github.com/mintmin0320)              |                                                    Design 김시은                                             |

## 4. 📝 문서화 링크 (테크 스팩, 트러블 슈팅, 개인 내용 정리)

### 1) [재현 : 연관관계 제거](https://handmadecoding.tistory.com/100)
### 2) [재현 : 양방향 연관관계 테스트](https://handmadecoding.tistory.com/99)
### 3) [재현 : Transactional 어디까지 사용해야 할까?](https://handmadecoding.tistory.com/105)
### 4) [재현 : SSE-알림 리팩토링](https://handmadecoding.tistory.com/108)
### 5) [이슬 : InnoDB의 외래 키 관리를 위한 락 확장으로 인해 데드락 발생](https://yiseull.tistory.com/32)
### 6) [이슬 : 투표 참여 기능에서 동시성 이슈 발생](https://yiseull.tistory.com/33)
### 7) [이슬 : Redis에서 문제가 발생할 경우 투표 로직이 정상적으로 처리되어도 에러 메시지 반환](https://github.com/Yiseull/dev-qna/blob/main/%5B%EA%B0%9C%EC%84%A0%5D%20Redis%20%EB%A1%9C%EC%A7%81%EC%9D%84%20%EC%9D%B4%EB%B2%A4%ED%8A%B8%20%EB%B9%84%EB%8F%99%EA%B8%B0%20%EC%B2%98%EB%A6%AC.md)
### 8) [이슬 : @SpringBootTest에서 LazyInitializationException 발생](https://github.com/Yiseull/dev-qna/blob/main/%5B%ED%8A%B8%EB%9F%AC%EB%B8%94%EC%8A%88%ED%8C%85%5D%20%40SpringBootTest%EC%97%90%EC%84%9C%20LazyInitializationException%20%EB%B0%9C%EC%83%9D.md)
### 9) [이슬 : 특정 상황에서 사용자에게 포인트를 지급하는 경우, 포인트 지급 메서드 직접 호출 vs 이벤트 처리](https://yiseull.tistory.com/31)
### 10) [병곤 : Blue/Green 전략으로 무중단 배포](https://night-pump-292.notion.site/Blue-Green-2daa88cee1f64762b2e5be5d37ce794a?pvs=74)
### 11) [병곤 : 채팅 목록 조회 개선 ](https://night-pump-292.notion.site/a2b4c94c98a74c7dad0b1fb2d88e12d6?pvs=74)
### 12) [병곤 : Nginx를 이용한 로드 밸런싱](https://night-pump-292.notion.site/Nginx-e75485c97cb24e9d8ebf6b7f7d73c164)
### 13) [병곤 : Nginx를 이용한 HTTPS 적용](https://night-pump-292.notion.site/LIME-HTTPS-8dde5eff6e6c4716ae68592bf0c44280?pvs=74)

## 5. ⚒️ 기술 스택

### 백엔드
<img src="https://github.com/bucket-back/bucket-back-backend/assets/98391539/6535b1c4-46af-4d00-b7ab-3e2fc9fda57c" width="80%" height="80%">

<img width="1017" alt="스크린샷 2024-02-27 오후 3 35 02" src="https://github.com/uju-in/lime-backend/assets/77893164/e93893d0-d034-4182-9a1c-b32754eb99b6">

## 6. 💫 CICD 파이프라인, 모니터링

### 업데이트 전 Ver.1
 <img width="2135" alt="스크린샷 2024-02-27 오후 3 34 34" src="https://github.com/uju-in/lime-backend/assets/77893164/aa7cee2a-d85f-41d3-8e00-d8294ad9b3df">
 <img width="985" alt="스크린샷 2024-02-27 오후 3 35 14" src="https://github.com/uju-in/lime-backend/assets/77893164/4b85d934-af44-4bb4-a592-094dac429372">

### 업데이트 후 Ver.2
<img width="1134" alt="스크린샷 2024-03-28 오후 7 22 31" src="https://github.com/uju-in/lime-backend/assets/77893164/f118361a-b373-4b5e-84a4-ab35575907e9">
<img width="1133" alt="스크린샷 2024-03-28 오후 7 22 44" src="https://github.com/uju-in/lime-backend/assets/77893164/dbdf0e9a-a9ba-4f53-8318-734df4690c0d">
<img width="1138" alt="스크린샷 2024-03-28 오후 7 22 53" src="https://github.com/uju-in/lime-backend/assets/77893164/ee2677c8-7ad1-42c5-bc45-eec4b7c72772">



