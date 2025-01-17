#ifndef UARTRINGBUFFER_H_
#define UARTRINGBUFFER_H_

#include "stm32f4xx_hal.h"

/* change the size of the buffer */
#define UART_BUFFER_SIZE 64

typedef struct
{
  unsigned char buffer[UART_BUFFER_SIZE];
  volatile unsigned int head;
  volatile unsigned int tail;
} ring_buffer;


/* the ISR for the uart. put it in the IRQ handler */
void uart_isr (UART_HandleTypeDef *huart);



/*** Depreciated For now. This is not needed, try using other functions to meet the requirement ***/
/* get the position of the given string within the incoming data.
 * It returns the position, where the string ends
 */
//uint16_t Get_position (char *string);

/* once you hit 'enter' (\r\n), it copies the entire string to the buffer*/
//void Get_string (char *buffer);



#endif /* UARTRINGBUFFER_H_ */
