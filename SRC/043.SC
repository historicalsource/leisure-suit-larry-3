;**
;**	Logics for script 43 -- Carving script
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 26, 1989
;**


(script#			CARVING)

(public
	CarvingScript		0
)


(define	vLcarving					711)

(local
	oldIllegalBits
)


(instance CarvingScript of Script
	(method (changeState newState)
		(switch (= state newState)

			(0
				(if (!= egoState NORMAL)
					(= cycles 1)
				else	
					(HandsOff)
					(= oldGameSpeed (theGame setSpeed 6))
					(= oldIllegalBits (ego illegalBits))
					(ego
						view			vLcarving,
						loop			0,
						cel			0,
						setCycle		EndLoop self,
					)
				)
				(Print "You use your razor sharp ginsu knife to carefully carve 
					the precious granadilla wood into what you hope will be 
					accepted as a primitive island native sculpture."
					#icon iWood 0 0
				)
			)

			(1
				(if (!= egoState NORMAL)
					(= cycles 1)
				else	
					(ego
						loop			1,
						cel			0,
						setCycle		Forward,
					)
					(= cycles (* 10 (NumCels ego)))	;**	Make 10 cuts 
				)
			)

			(2
				((Inventory at iWood)
					view				(if (>= filthLevel RISQUE) ivSculpture 
										else								ivSculptureModest),
				)
				(Format ((Inventory at iWood) name) "Erotic Sculpture")
				(theGame changeScore 50)
				(if (== egoState NORMAL)
					(HandsOn)
					(NormalEgo facingRight)
					(ego
						illegalBits		oldIllegalBits,
					)
					(theGame 
	  					setSpeed 		oldGameSpeed,
					)
				)
				(theGame 
					setScript 		FALSE,
				)
				(DisposeScript CARVING)
			)
		)
	)
)
