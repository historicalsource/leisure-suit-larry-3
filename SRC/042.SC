;**
;**	Logics for script 42 -- Lei script
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 4, 1989
;**


(script#				LEI)

(public
	LeiingScript		0
)


(define	vLmakingLei					710)
	(define	lProduceIt			0)
	(define	lWeaving				1)
	(define	lLeiDone				2)
	(define	lLeiIntoCoat		3)


(local
	oldIllegalBits
)


(instance LeiingScript of Script
	(method (changeState newState)
		(switch (= state newState)

			(0
				(HandsOff)
				(= oldGameSpeed (theGame setSpeed 6))
				(= oldIllegalBits (ego illegalBits))
				(ego
					view			vLmakingLei,
					loop			lProduceIt,
					cel			0,
					cycleSpeed	1,
					setCycle		EndLoop self,
				)
				(Print "You weave the wild orchids into a beautiful lei."
					#icon iOrchids 0 0
				)
			)

			(1
				(ego
					loop			lWeaving,
					cel			0,
					setCycle		Forward,
				)
				(delay 3)
			)

			(2
				(ego
					loop			lLeiDone,
				)
				(delay 2)
			)

			(3
				(ego
					loop			lLeiIntoCoat,
					cel			0,
					setCycle		EndLoop self,
				)
			)

			(4
				((Inventory at iOrchids)
					view		ivLei ,
				)
				(Format ((Inventory at iOrchids) name) "A Quick Lei")
				(if (not (TstFlg madeLei))
					(SetFlg madeLei)
					(theGame changeScore 50)
					(Print "(Welcome to the islands, Meester Laffer!)" LOW)
				else
					(Print "(Welcome BACK to the islands, Meester Laffer!)" LOW)
				)
				(NormalEgo facingRight)
				(ego
					illegalBits		oldIllegalBits,
				)
				(theGame 
					setScript	FALSE,
					setSpeed		oldGameSpeed,
				)
				(DisposeScript LEI)
			)
		)
	)
)

