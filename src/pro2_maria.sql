-- �����ȣ�� �޿��λ���� �־�� �ش����� �޿��� �λ�Ǵ� ���ν���(PL/pgSQL)
DELIMITER //

CREATE OR REPLACE PROCEDURE INCRE2(IN n INT, IN r DECIMAL)
BEGIN
    DECLARE newPay DECIMAL;
    
    SELECT SAL INTO newPay FROM EMP WHERE EMPNO = n;
    SET newPay := newPay + newPay * r / 100;
    UPDATE EMP SET SAL = newPay WHERE EMPNO = n;
    COMMIT;
END //

DELIMITER ;

-- call INCRE2(7839, 10);
-- select EMPNO, ENAME, SAL from EMP where EMPNO=7839;