# poo-2024-1-template-trabalho-agencia-viagens

Data de apresentação: veja no google classroom.

Trabalho em grupo com relatório e apresentação: veja regras de número de membros e normas para relatório no google classroom da turma.

O trabalho praticará dos seguintes conhecimentos do curso:
- Orientação a objetos em Java
- Threads
- Leitura / Escrita de Arquivos em Java
- Marcação de tempos e experimentação em Java
- Interface Gráfica (opcional)

## Tema

O tema do trabalho é o desenvolvimento de uma Plataforma de Reserva de Viagens.

Motivação/Problema: usuários entram na plataforma e solicitam um orçamento de viagem; depois eles avaliam e aceitam ou não esse orçamento (considerando valor e requisitos de qualidade dos hoteis). O orçamento envolve escolher o hotel mais barato disponível para a quantidade de estrelas pedidas pelo usuário (exemplo, se pediu no mínimo 3 estrelas, pode ser 3, 4 ou 5, mas não 1 ou 2). O orçamento também envolve pegar o ponto de saída do usuário e encontrar vôos mais baratos até chegar no destino. Exemplo: usuário mora no RIO e quer chegar em Palmas/TO (PMW), possivelmente ele precisará encontrar um vôo RIO x Belo Horizonte/MG (CNF) e outro CNF x PMW. Para simplificar, iremos considerar no máximo duas conexões. À medida que usuários aceitam ou não o orçamento, os hoteis e vôos podem ir se esgotando ou não, impactando assim os próximos usuários.

## Implementações

Existirão dois fluxos básicos, um mais lento (serial/sequencial) e outro mais rápido (paralelo/tempo real):
1) uma lista de usuários deverá, sequencialmente (sem threads), ser processada, sendo que a escolha do usuário aceitar ou não o orçamento (de acordo com valor disponível)
2) a mesma lista de usuários deve ser enviada ao sistema para reservas simultâneas (por exemplo, lançar blocos de B=10 usuários), então será importante verificar que não existam inconsistências, à medida que novos pedidos chegam e aceitam ou não, se isso não modifica o orçamento dado previamente ao usuário (no caso, é necessário enviar outro orçamento para que ele avalie novamente o novo preço).

Façam experimentos computacionais e meçam quanto tempo leva para executar um conjunto de:
- 100 clientes
- 1000 clientes
- 10000 clientes

Experimente até que o tempo de execução Sequencial seja de minutos. O algoritmo com threads deverá ser, naturalmente, mais rápido do que o sequencial, mas caso não seja, explique as razões.

IMPORTANTE: os grupos deverão especificar as classes e gerar os próprios arquivos para testes, explorando características de interesse, dentro das especificações do trabalho (ou seja, se o código está mais rápido no computador utilizado, pode ser necessário gerar arquivos maiores).

Veja arquivos em anexo, que devem ser lidos:
- formato-voos.csv
- formato-hoteis.csv
- formato-voos.csv

Os formatos buscam ser auto-explicáveis, mas caso tenham dúvidas, procurem o professor.


## Extras

Podem ser exploradas as seguintes questões extras no trabalho:

1. defina uma classe para Cliente Fidelidade, e acumule pontos a cada viagem dos clientes, ganhando um valor percentual em pontos
    * cliente fidelidade tem descontos em dinheiro ou pagamento diretamente com pontos
2. defina classes e relacionamentos entre Hoteis parceiros, que darão descontos para hospedagens em locais parceiros
3. faça o mesmo para aéreas parceiras
4. faça alocações para datas específicas desejadas pelos clientes, e não apenas nas "melhores datas" oferecidas pela sua Empresa de Viagens

# Licença

MIT License

Igor Machado Coelho

Copyleft 2024