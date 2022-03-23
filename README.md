## 기능 구현 목록 (Step 1 - 체스판 초기화)

### 체스판 도메인 목록

- Piece - 체스 말 하나(Blank 포함)
- Rank - 8개의 체스 말(Piece)로 이루어진 체스판의 가로 행
- Board - 8개의 Rank로 이루어진 8 * 8 보드판

### 체스 말(Piece 추상 클래스)

- 위치에 대한 상태 값을 가진다.
- move 행위를 가진다.
- 팀에 대한 상태 값을 가진다.

### 체스 말의 종류(Piece 구현체)

- Bishop, Knight, King, Blank, Rook, Pawn, Queen
- 표현 상태 값(위치, 팀)을 가진다.
- move 행위를 재정의한다.
- Blank는 move 호출 시, 예외를 발생시킨다.

### 체스판의 가로 행 (Rank)

- 각 격자 칸을 표현하는 Piece 리스트를 가진다.

### 체스판 전체(Board)

- 각 행을 표현하는 Rank 리스트를 가진다.

### View

- 게임 시작 안내 메시지 출력
- 게임 시작 키워드(start) 입력 시 초기화 된 체스판 출력
- 빈 칸은 ‘.’, 검은색 진영은 첫 번째 글자의 대문자, 흰색 진영은 첫 번째 글자의 소문자로 말의 상태 출력(Knight는 K가 아니라 N으로 출력)
- 게임 종료 키워드(end) 입력 시 프로그램 종료

## 우아한테크코스 코드리뷰

- [온라인 코드 리뷰 과정](https://github.com/woowacourse/woowacourse-docs/blob/master/maincourse/README.md)
