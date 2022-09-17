# KaKao Clone Coding 

## 프로젝트 기획
### 프로젝트 설명
카카오톡 서비스를 클론하므로써 서비스를 이루고 있는 아키텍처에 대한 학습

## 설계 고려사항
> 1. 실시간 채팅 기능의 구현
> 2. 채팅 기능의 안정성


## 아키텍처
![화면 캡처 2022-09-17 170644](https://user-images.githubusercontent.com/59110017/190847132-47dde153-0600-416b-89a1-5dd1a2207be6.png)

## 채팅 기능
> * Socket을 통해서 실시간 채팅 구현
> * Stomp를 통해서 실시간 통신의 안정성 확보

## 트러블 슈팅
### 채팅 기능

  * 적용 계기
    * 실시간 채팅 기능의 구현
  * 대안
    * Redis의 PUB/SUB 방식을 통해서 실시간 통신 구현
    * Socket stomp를 통해서 실시간 통신 구현
  * 문제점
    * Redis로 구현하기 위해서는 BE 와 FE 모두 배워야하기 때문에 러닝 커브가 높았음
      프로젝트 일정 상 많은 시간을 학습에서 할당할 수 없었음
  * 문제 해결
    * Socket 은 공식 문서가 많고 관련 프로젝트가 많아서 러닝 커브가 낮음

