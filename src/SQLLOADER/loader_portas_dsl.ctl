options ( skip=3 )
LOAD DATA
CHARACTERSET AL32UTF8
APPEND
INTO TABLE IVA_SOC.TBL_FAC_VELOX_PORTAS_DSL
FIELDS TERMINATED BY '|'  OPTIONALLY enclosed by '"' trailing NULLCOLS
(
UF,
L0CALIDADE,
ESTACAO,
ID_GRANITE_INTERNO,
FABRICANTE,
MODELO,
DSLAM,
RACK,
SHELF,
SLOT,
MODELO_PLACA,
PORTA,
MODEM,
VERTICAL,
HORIZONTAL,
REGUA,
PINO,
VERTICAL_EQN_VOZ,
HORIZONTAL_VOZ,
REGUA_VOZ,
PINO_VOZ,
STATUS,
SERVICO,
VELOCIDADE,
CIRCUITO
)