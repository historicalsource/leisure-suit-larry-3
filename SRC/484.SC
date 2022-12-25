;**
;**	Logics for room 484 -- Patti's Penthouse Apartment (ego is Patti)
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	January 27, 1990
;**


(script#				484)

(include	"480.sh")
(include	"480.inc")

(public
	rm484				0
)


(define	nearElevator		(& (ego onControl) cBrown))
(define	hideFirst			"Patti! There are people watching!!
										Move behind your dressing screen first.")

(enum												;**	RoomScript state values
	intro
	getDress
	putDress
	dressBackAgain
)


(local
	seenMsg
	[string 200]
)


(instance rm484 of Room
	(properties
		picture		480
		south			470
	)


	(method (init)
		(super	 	init)

		(aWine
			init,
			setCel			(if (InRoom iBottle) c0glasses else cTrayOnly),
		)
		(aDoor					
			init,
			setCel			LastCel,
			stopUpd,
		)
		(if (InRoom iPanties)		(aPanties	init))
		(if (InRoom iBra)				(aBra			init))
		(if (InRoom iPantyhose)		(aPantyhose	init))
		(aDress				init)

		(addToPics
			add 				atpTelescope,
			doit
		)

		(self
			setScript 		RoomScript,
		)


		(NormalEgo)
		(= egoState NORMAL)					;**	Hope this line works! 1/27/90
		(if (== prevRoomNum 470)
			(ego
				posn				159 188,
				loop				facingNorth,
				init,
			)
		else
			(if (or	(TstFlg tookShortcut)
						(== prevRoomNum 0))
				(ClrFlg tookShortcut)

				(systemWindow
					color 			textColor,
					back				backgroundColor,
				)
				(TheMenuBar	
					draw,
					state 			TRUE,
				)
				(StatusLine enable)
				(ClrFlg preventAutoSave)
				(ClrFlg noCursor)
			)



			;**	
			;**	This is the big operation!
			;**	

			(= normalEgoView vPatti)
			(= egoIsPatti TRUE)
			(= egoName (Format @egoNameBuffer "Patti"))
			(= newspaperState NSlMissing)

			;**	Set up the piano bar.

			(PutInRoom	iMoney		450)		
			((Inventory at iMoney) view ivTips)
			(Format ((Inventory at iMoney) name) "$43.00 in Tips")
			(PutInRoom	iMarker		450)		

			(PutInRoom	iKnife		LIMBO) ;**	Dump Larry's stuff
			(PutInRoom	iTowel		LIMBO)
			(PutInRoom	iKeycard		LIMBO)

			(ego get iKey) 					;**	the only thing ego gets to keep!
			((Inventory at iKey) view ivPattiKey)
			(Format ((Inventory at iKey) name) "Your Key")

			;**	
			;**	This is the end of the big operation!
			;**	



 			(theGame setSpeed oldGameSpeed)
			(Load VIEW vPatti)
			(ego
				view				vPinSheet,
				posn				160 59,
				loop				facingDown,
				baseSetter		SheetBase,
				init,
			)
			(music
				number			sPmissesL,
				loop				bgMusicLoops,
				play,
			)
		)
	)


	(method (newRoom n)
		(music fade)
		(super newRoom n)
		(music fade)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)

		(if nearElevator
			(if (and	(not seenMsg)
						(!= (ego view) normalEgoView))
				(= seenMsg TRUE)
				(Print "You can't leave dressed in nothing but that
					blue sheet!")
			)
		else
			(= seenMsg FALSE)
		)
		
		(if (!= (ego view) vPatti)
			(ego observeControls cLtMagenta)
		else
			(ego ignoreControls 	cLtMagenta)
		)
	)


	(method (handleEvent event)
		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'remove, (take<off)/dress')
				(cond
					((!= egoState NORMAL)
						(NotNow)
					)
					((not (ego has iDress))
						(DontHave)
					)
					((and (ego has iBra)
							(ego has iPanties)
							(ego has iPantyhose))
						(Print "Although you enjoy walking around your penthouse
							nude, you don't want to do it now.")
					)
					((not nearClothes)
						(Print hideFirst)
					)
					(else
						(self changeState putDress)
					)
				)
			)

			((Said 'wear, remove, (on<put), (change<into), (put<on), get>')
				(cond
					((!= egoState NORMAL)
						(NotNow)
						(event claimed TRUE)
					)

					((Said '/sheet')
						(if (== (ego view) vPinSheet)
							(Print "You're wearing it!")
						else
							(Print "No, it's served you well; 
								now just leave it there.")
						)
					)

					((Said '/wine, bottle')				
						(cond
							((not (InRoom iBottle))
								(AlreadyTook)
							)
							((not nearBottle)
								(NotClose)
							)
							(else
								(Ok)
								(aWine
									setCel		cTrayOnly,
									stopUpd,
								)
								(theGame changeScore 25)
								(ego
									get 		iBottle,
								)
							)
						)
					)

					((Said '/panties')
						(cond
							((not (InRoom iPanties))	;**	default to curRoomNum
								(AlreadyTook)
							)
							((not nearClothes)
								(Print hideFirst)
							)
							(else
								(Ok)
								(ego get iPanties)
								(theGame changeScore 20)
								(aPanties dispose)
								(Print "You've always enjoyed the feeling of black
									lace.")
							)
						)
					)

					((Said '/bra')
						(cond
							((not (InRoom iBra))	;**	default to curRoomNum
								(AlreadyTook)
							)
							((not nearClothes)
								(Print hideFirst)
							)
							((ego has iDress)
								(Print "You want to wear your bra on the outside
									of your dress?")
							)
							(else
								(Ok)
								(ego get iBra)
								(theGame changeScore 20)
								(aBra dispose)
								(Print "You look good in black!")
							)
						)
					)

					((Said '/pantyhose')
						(cond
							((!= egoState NORMAL)
								(NotNow)
							)
							((not (InRoom iPantyhose))	;**	default to curRoomNum
								(AlreadyTook)
							)
							((not nearClothes)
								(Print hideFirst)
							)
							(else
								(Ok)
								(ego get iPantyhose)
								(theGame changeScore 20)
								(aPantyhose dispose)
								(Print "Patti! You've got \"L'eggs!\"")
							)
						)
					)

					((Said '/dress')
						(cond
							((!= egoState NORMAL)
								(NotNow)
							)
							((not (InRoom iDress))	;**	default to curRoomNum
								(AlreadyTook)
							)
							((not nearClothes)
								(Print hideFirst)
							)
							(else
								(self changeState getDress)
							)
						)
					)
				)
			)

			((Said 'look<under')
				(Print "There's nothing under there!")
			)

			((Said 'strip, undress')
				(Print "Perhaps you could do that later.")
			)

			((Said 'drop, lower, remove, (take<off)/sheet')
				(Print "Move behind the screen, Patti!
					No one here wants to see your naked, sensuous body.")
			)

			((Said '/shoe')
				(Print "You put on your shoes when you put on your dress, Patti.")
			)

			((Said '/tray')
				(Print "You decide to leave the tray there.
					Let room service clean up.")
			)

			((Said 'play/piano')
				(Print "You consider fighting your depressed feeling with
					music, but instead realize: finding Larry Laffer is
					the only medicine you need.")
			)

			((Said 'close/door')
				(Print "There's no need, Patti.")
			)

			((Said 'look>')
				(cond
					((Said '/screen, (table<dressing), clothes')
						(cond
							((InRoom iPantyhose)
								(Print "A pair of pantyhose lies on your dressing
									table.")
							)
							((InRoom iPanties)
								(Print "A pair of black lace panties rests on the
									dressing table beside the screen.")
							)
							((InRoom iBra)
								(Print "A black lace brassiere is all that is left
									on the dressing table.")
							)
							(else
								(Print "Your dressing table is empty.")
							)
						)
						(if (InRoom iDress)
							(Print "Your white gown with the long slit hangs on a
								hanger on your dressing screen.")
						)
					)

					((Said '/table')
						(if (InRoom iBottle) 
							(Print "The now-empty bottle of wine that Larry brought
								you rests on the table at the foot of your bed.")
						else
							(Print "The table at the foot of your bed contains
								nothing but an empty room-service tray.")
						)
						(if (or	(InRoom iPantyhose)
									(InRoom iPanties)
									(InRoom iBra))
							(Print "Some of your clothing lies on your
								dressing table, beside your dressing screen.")
						else
							(Print "Your dressing table is empty.")
						)
					)

					((Said '/tray')
						(if (InRoom iBottle) 
							(Print "The now-empty wine bottle rests on 
								the table at the foot of your bed.")
						else
							(Print "The table at the foot of your bed contains
								nothing but an empty room-service tray.")
						)
					)			

					((and (InRoom iBottle)
							(Said '/bottle'))
						(Print "The now-empty wine bottle rests on 
							the table at the foot of your bed.")
					)			

					((Said '/bed')
						(Print "That's where it happened, all right!")
					)

					((Said '/piano')
						(Print "Wasn't it nice of the casino management to
							rent a piano for your suite.")
					)

					((Said '/telescope')
						(Print "There's no use looking for Larry now,
							you know where he went.
							Now go find him!")
					)

					((Said '/light')
						(Print "The lamps are just hanging around here.")
					)

					((Said '/plant')
						(Print "You're not interested in plants, Patti!")
					)

					((Said '/balcony, view, window')
						(Print "The view is spectacular, 
							but you want to see your Larry!")
					)

					((Said '[/room]')			
						(Print "You'll never be able to look at this suite again
							without thinking of your Larry!")
					)
				)
			)

			((Said '/clothes')
				(Print "You must refer specifically to each article of clothing.")
			)
		)
	)


	(method (changeState newState)
		(switch (= state newState)

			(intro
			)


			;**	take the dress

			(getDress
				(Ok)
				(ego 
					get 				iDress,
					view				vPatti,
				)
				(theGame changeScore 10)
				(Print "You slide your dress over the screen and
					drop the sheet on the floor.")
				(aDress
					setCycle			EndLoop self,
				)
				(++ state)						;**	share code
			)


			;**	Return the dress to the screen.

			(putDress
				(Ok)
				(PutInRoom iDress)
				(ego view vPinSheet)
				(theGame changeScore -10)
				(Print "You grab the sheet again, 
					and hang your dress back on the screen.")
				(aDress
					setCycle			BegLoop self,
				)
			)

			(dressBackAgain
				(aDress				stopUpd)
			)
		)
	)
)

(instance SheetBase of Code
	(method (doit)
		(ego brBottom		(+ (ego y) 1 ))
		(ego brTop			(- (ego brBottom) 2))
		(ego brLeft			(- (ego x) 9))
		(ego brRight		(+ (ego x) 9))
	)
)
