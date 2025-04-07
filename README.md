# Prometheus 및 Grafana을 활용한 Spring app 모니터링
<br>
SpringBoot 애플리케이션을 Prometheus, Grafana와 연동하여 메트릭 수집 및 시각화가 가능한 모니터링 시스템을 구축했습니다. <br><br>
이 시스템은 VM 환경(NAT 설정) 내에서, Docker 이미지 실행과 IP 및 포트포워딩 설정을 통해 네트워크 연결을 구성하였습니다.

<br><br>

### 📆 프로젝트 기간
- 2025.04.04 ~ 04.06
<br>

## ⚙ 구성요소

### 1. Spring Boot 애플리케이션

- 역할 : 비즈니스 로직 제공 + 메트릭 제공
- 설정 : <br>
    - /actuator/prometheus 엔드포인트 활성화 (application.yaml)
    - micrometer-registry-prometheus 의존성 추가

- 커스텀 메트릭 예 :
    - /countUp 요청마다 custom_counter 메트릭 증가
    - 로그 출력을 통해 호출 확인 가능

<br>  

### 2. Docker

- Spring 애플리케이션을 Docker 이미지로 빌드 후 실행
- -p 8080:8080으로 포트 바인딩하여 외부 접근 허용
- 0.0.0.0 바인딩으로 모든 IP 접근 가능하게 설정

<br>

### 3. Prometheus

- 역할 : 메트릭 수집기
- 설정 : <br>
    - `http://127.0.0.1:8080/actuator/prometheus`를 통해 메트릭 수집
    - /etc/prometheus/prometheus.yml <br>
    
![image](https://github.com/user-attachments/assets/4303be1b-a5bc-45f7-91ee-cb503f46ad6f)

<br>

### 4. Grafana

- 역할 : 시각화 도구
- 데이터 소스 : Prometheus 선택
- 주요 패널 예 : <br>
    - process_cpu_usage (CPU 사용률)
    - rate(custom_counter_total[1m]) (요청 수)
    - jvm_memory_used_bytes (JVM 메모리 사용)
    - http_server_requests_seconds_count (요청 트래픽)

<br>

## 💻 환경 세팅

- Spring Boot 애플리케이션 - Docker 컨테이너로 실행 <br>
( seokhyejin00/spring-app:latest 이미지 ) 

<br>

- Prometheus - VM에 직접 설치 <br>
( prometheus-3.2.1.linux-amd64.tar.gz )

<br>

- Grafana - VM에 직접 설치 <br>
( sudo apt install grafana )

<br>

- VM 환경 구성 - VirtualBox + NAT 설정

<br>

## 🎨 시나리오 흐름

1. 사용자가 /countUp API 호출

2. SpringBoot에서 custom_counter 증가

3. Prometheus가 127.0.0.1:8080에서 주기적으로 메트릭 수집

4. Grafana가 Prometheus 데이터 기반으로 대시보드 갱신

5. 사용자는 Grafana에서 실시간 지표 확인

<br>

## 프로젝트 시연

🔹 spring-app 이미지 실행 <br><br>
![image](https://github.com/user-attachments/assets/92057c42-203c-4a31-abda-eaa98a84e08c)

![image](https://github.com/user-attachments/assets/129d0eec-02fe-4b53-8900-95902c01a107)

<br>

🔹 Prometheus 실행 <br><br>
![image](https://github.com/user-attachments/assets/40f5e8e2-815e-42a6-942b-467bfab82515)

<br>

🔹 Grafana 실행 <br><br>
![image](https://github.com/user-attachments/assets/8d9ff131-7cbb-472a-a2af-6fa7e39fb362)

<br>

🔹 /countUp 엔드포인트 호출로 custom_counter 메트릭 값 증가 유도 <br><br>
![image](https://github.com/user-attachments/assets/c4ce7cfa-36cb-4bef-8c3e-2787c8aaafb8)

<br>

🔹 Grafana 대시보드 확인 <br><br>
![image](https://github.com/user-attachments/assets/e939cba2-a973-4dfe-b21f-190ed02a09ff)

<br>

## 트러블 슈팅

<br>

### 🔴 Docker 컨테이너의 8080 포트가 Prometheus에서 인식되지 않음

<br>

![image](https://github.com/user-attachments/assets/50f171d6-c0d0-49dd-bf5f-352cf296607a)

#### 🟢 Docker 이미지와 Prometheus를 같은 서버 상에서 작동시킴으로서 해결함
⇒ 이전에는 이미지를 별도 서버에서 실행하면서 동일한 네트워크 설정(bridge mode)을 적용하지 않아 <br> Prometheus가 메트릭 엔드포인트에 접근하지 못하는 문제가 발생함

![화면 캡처 2025-04-06 094314](https://github.com/user-attachments/assets/0c79fe03-2678-4564-8e0c-f7ad6f955284)

<br>

### 🔴 Grafana에서 PromQL query가 작동하지 않음

<br>

![image](https://github.com/user-attachments/assets/86aef58f-8ebb-4191-adf1-d5e03d7c2a6d)

![화면 캡처 2025-04-06 235320](https://github.com/user-attachments/assets/d8aef7ed-5eb7-4bc9-9569-60563a80037c)








  



