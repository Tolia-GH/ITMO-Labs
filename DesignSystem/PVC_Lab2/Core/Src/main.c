/* USER CODE BEGIN Header */
/**
  ******************************************************************************
  * @file           : main.c
  * @brief          : Main program body
  ******************************************************************************
  * @attention
  *
  * Copyright (c) 2024 STMicroelectronics.
  * All rights reserved.
  *
  * This software is licensed under terms that can be found in the LICENSE file
  * in the root directory of this software component.
  * If no LICENSE file comes with this software, it is provided AS-IS.
  *
  ******************************************************************************
  */
/* USER CODE END Header */
/* Includes ------------------------------------------------------------------*/
#include "main.h"
#include "can.h"
#include "usart.h"
#include "gpio.h"

/* Private includes ----------------------------------------------------------*/
/* USER CODE BEGIN Includes */

/* USER CODE END Includes */

/* Private typedef -----------------------------------------------------------*/
/* USER CODE BEGIN PTD */

/* USER CODE END PTD */

/* Private define ------------------------------------------------------------*/
/* USER CODE BEGIN PD */
/* USER CODE END PD */

/* Private macro -------------------------------------------------------------*/
/* USER CODE BEGIN PM */

/* USER CODE END PM */

/* Private variables ---------------------------------------------------------*/

/* USER CODE BEGIN PV */

/* USER CODE END PV */

/* Private function prototypes -----------------------------------------------*/
void SystemClock_Config(void);
/* USER CODE BEGIN PFP */

/* USER CODE END PFP */

/* Private user code ---------------------------------------------------------*/
/* USER CODE BEGIN 0 */

// 防抖相关变量
uint32_t last_button_press_time = 0;
uint8_t isButtonEnabled = 0;
uint8_t didUpdatedAtThatTime = 0;
const uint32_t debounce_delay = 10; // 防抖延迟（单位：毫秒）

uint8_t sparkling_mode = 0;

uint32_t last_sparking_time_1 = 0;
uint8_t sparkMode_1 = 0;
const uint32_t sparkling_delay_1 = 50; // 防抖延迟（单位：毫秒）
uint32_t last_sparking_time_2 = 0;
uint8_t sparkMode_2 = 0;
const uint32_t sparkling_delay_2 = 200; // 防抖延迟（单位：毫秒）
uint32_t last_sparking_time_3 = 0;
uint8_t sparkMode_3 = 0;
const uint32_t sparkling_delay_3 = 800; // 防抖延迟（单位：毫秒）


const uint32_t sparkling_delay_low = 200;

const uint32_t sparkling_delay_medium = 600;

const uint32_t sparkling_delay_high = 1000;

uint8_t sparkMode_0 = 0;
uint32_t last_sparking_time_0 = 0;

char spark_order_5[10];
uint32_t sparkling_delay_5;

char spark_order_6[10];
uint32_t sparkling_delay_6;

char spark_order_7[10];
uint32_t sparkling_delay_7;

char spark_order_8[10];
uint32_t sparkling_delay_8;

uint8_t count_new_mode = 0;



void Check_Button(void) {
	uint32_t current_time = HAL_GetTick();
	if (HAL_GPIO_ReadPin(GPIOC, GPIO_PIN_15) != isButtonEnabled) {
		isButtonEnabled = HAL_GPIO_ReadPin(GPIOC, GPIO_PIN_15);
		last_button_press_time = current_time;
		didUpdatedAtThatTime = 0;
	} else {
		if (current_time - last_button_press_time > debounce_delay && HAL_GPIO_ReadPin(GPIOC, GPIO_PIN_15) && didUpdatedAtThatTime == 0) {
			sparkling_mode = (sparkling_mode + 1) % 8;
			didUpdatedAtThatTime = 1;
		}
	}
}

void Spark(void) {
	uint32_t current_time = HAL_GetTick();

	if (sparkling_mode == 0) {
		mode_0();
	}
	if (sparkling_mode == 1) {
		mode_1(current_time);
	}
	if (sparkling_mode == 2) {
		mode_2(current_time);
	}
	if (sparkling_mode == 3) {
		mode_3(current_time);
	}
	if (sparkling_mode == 4) {
		mode(current_time, spark_order_5, sparkling_delay_5);
	}
	if (sparkling_mode == 5) {
		mode(current_time, spark_order_6, sparkling_delay_6);
	}
	if (sparkling_mode == 6) {
		mode(current_time, spark_order_7, sparkling_delay_7);
	}
	if (sparkling_mode == 7) {
		mode(current_time, spark_order_8, sparkling_delay_8);
	}
}

void mode(uint32_t current_time, char *order, uint32_t delay) {
	HAL_GPIO_WritePin(GPIOD, GPIO_PIN_15, GPIO_PIN_RESET);
	HAL_GPIO_WritePin(GPIOD, GPIO_PIN_14, GPIO_PIN_RESET);
	HAL_GPIO_WritePin(GPIOD, GPIO_PIN_13, GPIO_PIN_RESET);


	if (current_time - last_sparking_time_0 > delay){
		sparkMode_0 = (sparkMode_0 + 1) % strlen(order);
		last_sparking_time_0 = current_time;
	}


	if (order[sparkMode_0] == 'g') {
		HAL_GPIO_WritePin(GPIOD, GPIO_PIN_15, GPIO_PIN_RESET);
		HAL_GPIO_WritePin(GPIOD, GPIO_PIN_14, GPIO_PIN_RESET);
		HAL_GPIO_WritePin(GPIOD, GPIO_PIN_13, GPIO_PIN_SET);
	}
	if (order[sparkMode_0] == 'y') {
		HAL_GPIO_WritePin(GPIOD, GPIO_PIN_15, GPIO_PIN_RESET);
		HAL_GPIO_WritePin(GPIOD, GPIO_PIN_14, GPIO_PIN_SET);
		HAL_GPIO_WritePin(GPIOD, GPIO_PIN_13, GPIO_PIN_RESET);
	}
	if (order[sparkMode_0] == 'r') {
		HAL_GPIO_WritePin(GPIOD, GPIO_PIN_15, GPIO_PIN_SET);
		HAL_GPIO_WritePin(GPIOD, GPIO_PIN_14, GPIO_PIN_RESET);
		HAL_GPIO_WritePin(GPIOD, GPIO_PIN_13, GPIO_PIN_RESET);
	}
	if (order[sparkMode_0] == 'n') {
		HAL_GPIO_WritePin(GPIOD, GPIO_PIN_15, GPIO_PIN_RESET);
		HAL_GPIO_WritePin(GPIOD, GPIO_PIN_14, GPIO_PIN_RESET);
		HAL_GPIO_WritePin(GPIOD, GPIO_PIN_13, GPIO_PIN_RESET);
	}
}

void mode_0(void) {
	HAL_GPIO_WritePin(GPIOD, GPIO_PIN_15, GPIO_PIN_RESET);
	HAL_GPIO_WritePin(GPIOD, GPIO_PIN_14, GPIO_PIN_SET);
	HAL_GPIO_WritePin(GPIOD, GPIO_PIN_13, GPIO_PIN_SET);
}

void mode_1(uint32_t current_time) {
	if (current_time - last_sparking_time_1 > sparkling_delay_1){
		sparkMode_1 = (sparkMode_1 + 1) % 2;
		last_sparking_time_1 = current_time;
	}

	if (sparkMode_1) {
		HAL_GPIO_WritePin(GPIOD, GPIO_PIN_15, GPIO_PIN_RESET);
		HAL_GPIO_WritePin(GPIOD, GPIO_PIN_14, GPIO_PIN_SET);
		HAL_GPIO_WritePin(GPIOD, GPIO_PIN_13, GPIO_PIN_RESET);
	} else {
		HAL_GPIO_WritePin(GPIOD, GPIO_PIN_15, GPIO_PIN_SET);
		HAL_GPIO_WritePin(GPIOD, GPIO_PIN_14, GPIO_PIN_RESET);
		HAL_GPIO_WritePin(GPIOD, GPIO_PIN_13, GPIO_PIN_SET);
	}
}


void mode_2(uint32_t current_time) {
	if (current_time - last_sparking_time_2 > sparkling_delay_2){
		sparkMode_2 = (sparkMode_2 + 1) % 2;
		last_sparking_time_2 = current_time;
	}

	if (sparkMode_2) {
		HAL_GPIO_WritePin(GPIOD, GPIO_PIN_15, GPIO_PIN_RESET);
		HAL_GPIO_WritePin(GPIOD, GPIO_PIN_14, GPIO_PIN_SET);
		HAL_GPIO_WritePin(GPIOD, GPIO_PIN_13, GPIO_PIN_SET);
	} else {
		HAL_GPIO_WritePin(GPIOD, GPIO_PIN_15, GPIO_PIN_SET);
		HAL_GPIO_WritePin(GPIOD, GPIO_PIN_14, GPIO_PIN_RESET);
		HAL_GPIO_WritePin(GPIOD, GPIO_PIN_13, GPIO_PIN_SET);
	}
}

void mode_3(uint32_t current_time) {
	if (current_time - last_sparking_time_3 > sparkling_delay_3){
		sparkMode_3 = (sparkMode_3 + 1) % 2;
		last_sparking_time_3 = current_time;
	}

	if (sparkMode_3) {
		HAL_GPIO_WritePin(GPIOD, GPIO_PIN_15, GPIO_PIN_RESET);
		HAL_GPIO_WritePin(GPIOD, GPIO_PIN_14, GPIO_PIN_SET);
		HAL_GPIO_WritePin(GPIOD, GPIO_PIN_13, GPIO_PIN_SET);
	} else {
		HAL_GPIO_WritePin(GPIOD, GPIO_PIN_15, GPIO_PIN_SET);
		HAL_GPIO_WritePin(GPIOD, GPIO_PIN_14, GPIO_PIN_RESET);
		HAL_GPIO_WritePin(GPIOD, GPIO_PIN_13, GPIO_PIN_RESET);
	}
}

void setmode(char *order, uint32_t delay) {
	count_new_mode = (count_new_mode + 1) % 4;

	if (count_new_mode == 1) {
		strcpy(spark_order_5, order);
		sparkling_delay_5 = delay;
	}
	if (count_new_mode == 2) {
		strcpy(spark_order_6, order);
		sparkling_delay_6 = delay;
	}
	if (count_new_mode == 3) {
		strcpy(spark_order_7, order);
		sparkling_delay_7 = delay;
	}
	if (count_new_mode == 0) {
		strcpy(spark_order_8, order);
		sparkling_delay_8 = delay;
	}
}

void process_command(char *cmd, int *index) {
	//sprintf(cmd, "%s", cmd);
	HAL_UART_Transmit( &huart6, (uint8_t *) cmd, strlen(cmd), 10);
	HAL_UART_Transmit( &huart6, (uint8_t *) "\r\n", 2, 10);

	char *command;
	command = strtok(cmd, " ");
//	HAL_UART_Transmit( &huart6, (uint8_t *) command, strlen(command), 10);
//	HAL_UART_Transmit( &huart6, (uint8_t *) "\r\n", 2, 10);
	char *args;
	args= strtok(NULL, "");
//	HAL_UART_Transmit( &huart6, (uint8_t *) args, strlen(args), 10);
//	HAL_UART_Transmit( &huart6, (uint8_t *) "\r\n", 2, 10);

	if (strcmp(command, "set")==0) {
		if (strcmp(args, "1")==0) {
			sparkling_mode = 0;
		}
		if (strcmp(args, "2")==0) {
			sparkling_mode = 1;
		}
		if (strcmp(args, "3")==0) {
			sparkling_mode = 2;
		}
		if (strcmp(args, "4")==0) {
			sparkling_mode = 3;
		}
		if (strcmp(args, "5")==0) {
			sparkling_mode = 4;
		}
		if (strcmp(args, "6")==0) {
			sparkling_mode = 5;
		}
		if (strcmp(args, "7")==0) {
			sparkling_mode = 6;
		}
		if (strcmp(args, "8")==0) {
			sparkling_mode = 7;
		}

	}
	if (strcmp(command, "new")==0) {
		char msg[] = "Please choose spark delay:\n1 - low(200)\n2 - medium(600)\n3 - high(1000)\n";
		HAL_UART_Transmit( &huart6, (uint8_t *) msg, strlen(msg), 100);

		char index = 0;
		while (index != '\r') {
			if( HAL_OK == HAL_UART_Receive( &huart6, (uint8_t *) &index, 1, 1 ) ) {
     			HAL_UART_Transmit( &huart6, (uint8_t *) &index, 1, 10 );
				HAL_UART_Transmit( &huart6, (uint8_t *) "\r\n", 2, 10);
				if (index == '1') {
					setmode(args, sparkling_delay_low);
				}
				if (index == '2') {
					setmode(args, sparkling_delay_medium);
				}
				if (index == '3') {
					setmode(args, sparkling_delay_high);
				}
			}
		}
	}
	return;
}

uint8_t count_char(char *buffer) {
	int count = 0;
	for(int i = 0; i < sizeof(buffer); i++) {
		if (buffer[i]!='\0') {
			count++;
		} else {
			break;
		}
	}
	return count;
}


/* USER CODE END 0 */

/**
  * @brief  The application entry point.
  * @retval int
  */
int main(void)
{
  /* USER CODE BEGIN 1 */

  /* USER CODE END 1 */

  /* MCU Configuration--------------------------------------------------------*/

  /* Reset of all peripherals, Initializes the Flash interface and the Systick. */
  HAL_Init();

  /* USER CODE BEGIN Init */

  /* USER CODE END Init */

  /* Configure the system clock */
  SystemClock_Config();

  /* USER CODE BEGIN SysInit */

  /* USER CODE END SysInit */

  /* Initialize all configured peripherals */
  MX_GPIO_Init();
  MX_CAN2_Init();
  MX_USART6_UART_Init();
  /* USER CODE BEGIN 2 */

  /* USER CODE END 2 */

  /* Infinite loop */
  /* USER CODE BEGIN WHILE */

  int index = 0;
  char c;
  char buffer[32];

  while (1)
  {
//	  HAL_UART_Transmit( &huart6, (uint8_t *)s, sizeof( s ), 10);
//	  HAL_Delay( 1000 );

	  Check_Button();
	  Spark();

	  if( HAL_OK == HAL_UART_Receive( &huart6, (uint8_t *) &c, 1, 1 ) )
	  {
		  if (c == '\r') {

			  buffer[index] = '\0';
			  HAL_UART_Transmit( &huart6, (uint8_t *) "\r\n", 2, 10);
			  //HAL_UART_Transmit( &huart6, (uint8_t *) buffer, index, 10);
			  process_command(buffer, index);
			  //HAL_UART_Transmit( &huart6, (uint8_t *) "\r\n", 2, 10);
			  index = 0;
			  memset(buffer, '\0', sizeof(buffer));


		  } else if (c != '\n') {
			  HAL_UART_Transmit( &huart6, (uint8_t *) &c, 1, 10 );
		  	  buffer[index++] = c;
		  }
	  }

    /* USER CODE END WHILE */

    /* USER CODE BEGIN 3 */
  }
  /* USER CODE END 3 */
}

/**
  * @brief System Clock Configuration
  * @retval None
  */
void SystemClock_Config(void)
{
  RCC_OscInitTypeDef RCC_OscInitStruct = {0};
  RCC_ClkInitTypeDef RCC_ClkInitStruct = {0};

  /** Configure the main internal regulator output voltage
  */
  __HAL_RCC_PWR_CLK_ENABLE();
  __HAL_PWR_VOLTAGESCALING_CONFIG(PWR_REGULATOR_VOLTAGE_SCALE3);

  /** Initializes the RCC Oscillators according to the specified parameters
  * in the RCC_OscInitTypeDef structure.
  */
  RCC_OscInitStruct.OscillatorType = RCC_OSCILLATORTYPE_HSI;
  RCC_OscInitStruct.HSIState = RCC_HSI_ON;
  RCC_OscInitStruct.HSICalibrationValue = RCC_HSICALIBRATION_DEFAULT;
  RCC_OscInitStruct.PLL.PLLState = RCC_PLL_NONE;
  if (HAL_RCC_OscConfig(&RCC_OscInitStruct) != HAL_OK)
  {
    Error_Handler();
  }

  /** Initializes the CPU, AHB and APB buses clocks
  */
  RCC_ClkInitStruct.ClockType = RCC_CLOCKTYPE_HCLK|RCC_CLOCKTYPE_SYSCLK
                              |RCC_CLOCKTYPE_PCLK1|RCC_CLOCKTYPE_PCLK2;
  RCC_ClkInitStruct.SYSCLKSource = RCC_SYSCLKSOURCE_HSI;
  RCC_ClkInitStruct.AHBCLKDivider = RCC_SYSCLK_DIV1;
  RCC_ClkInitStruct.APB1CLKDivider = RCC_HCLK_DIV1;
  RCC_ClkInitStruct.APB2CLKDivider = RCC_HCLK_DIV1;

  if (HAL_RCC_ClockConfig(&RCC_ClkInitStruct, FLASH_LATENCY_0) != HAL_OK)
  {
    Error_Handler();
  }
}

/* USER CODE BEGIN 4 */

/* USER CODE END 4 */

/**
  * @brief  This function is executed in case of error occurrence.
  * @retval None
  */
void Error_Handler(void)
{
  /* USER CODE BEGIN Error_Handler_Debug */
  /* User can add his own implementation to report the HAL error return state */
  __disable_irq();
  while (1)
  {
  }
  /* USER CODE END Error_Handler_Debug */
}

#ifdef  USE_FULL_ASSERT
/**
  * @brief  Reports the name of the source file and the source line number
  *         where the assert_param error has occurred.
  * @param  file: pointer to the source file name
  * @param  line: assert_param error line source number
  * @retval None
  */
void assert_failed(uint8_t *file, uint32_t line)
{
  /* USER CODE BEGIN 6 */
  /* User can add his own implementation to report the file name and line number,
     ex: printf("Wrong parameters value: file %s on line %d\r\n", file, line) */
  /* USER CODE END 6 */
}
#endif /* USE_FULL_ASSERT */
